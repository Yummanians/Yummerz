<script lang="ts">
    import { onMount } from 'svelte';
    import {
        fetchRecipes,
        fetchIngredients, 
        createRecipe,
        updateRecipe,
        deleteRecipe,
        uploadMarkdown,
        type Recipe,
    } from '$lib/services/recipe_api';
    import '$lib/styles/global.css';
    import FileUpload from '../../components/FileUploader.svelte';

    interface Ingredient {
        id: number;
        name: string;
        calories: number; 
    }

    let recipes: Recipe[] = [];
    let globalIngredients: Ingredient[] = []; 
    
    // Form State
    let name = '';
    let ingredients = '';
    let instructions = '';
    
    // NEW: Form State for new tasks
    let rating = 0;
    let notes = '';
    let imageBase64 = '';
    
    let editingRecipeId: number | null = null;
    let searchTerm = '';
    let fileInput: any;
    let imageInput: HTMLInputElement; // Reference for the image file input
    
    let kolicina = 1;
    let dailyTargetKcal = 2000; 

    let showIngredientModal = false;
    let ingredientSearch = '';
    
    $: filteredIngredientsList = globalIngredients.filter((i) =>
        i.name.toLowerCase().includes(ingredientSearch.toLowerCase())
    );

    async function loadData() {
        try {
            recipes = await fetchRecipes(searchTerm);
            const result = await fetchIngredients(''); 
            globalIngredients = Array.isArray(result) ? result : [];
        } catch (e) {
            console.error("Napaka pri nalaganju podatkov:", e);
        }
    }

    onMount(loadData);

    $: if (searchTerm !== undefined) loadData();

    // ... calculateNutrition and scaleIngredients functions remain the same ...
    function calculateNutrition(recipeIngredientsText: string) {
        // ... (keep existing code)
        let totalKcal = 0;
		if (!recipeIngredientsText) return { total: 0, percent: 0 };
		const lines = recipeIngredientsText.split('\n');
		for (const line of lines) {
			const match = line.match(/(\d+)(?:g)?\s+(.*)/i) || [null, '100', line.trim()];
			const quantity = parseFloat(match[1] || '100'); 
			const ingredientName = match[2]?.trim().toLowerCase();
			if (ingredientName) {
				const found = globalIngredients.find(i => i.name.toLowerCase() === ingredientName);
				if (found) { totalKcal += (quantity / 100) * found.calories; }
			}
		}
		return { total: Math.round(totalKcal), percent: Math.round((totalKcal / dailyTargetKcal) * 100) };
    }

    function scaleIngredients(text: string, multiplier: number): string {
         if (!multiplier || multiplier === 1) return text;
		return text.replace(/\b\d+(\.\d+)?\b/g, (match) =>
			(parseFloat(match) * multiplier).toString()
		);
    }

    // NEW: Convert file to Base64
    const convertBase64 = (file: File) => {
        return new Promise<string>((resolve, reject) => {
            const fileReader = new FileReader();
            fileReader.readAsDataURL(file);
            fileReader.onload = () => {
                resolve(fileReader.result as string);
            };
            fileReader.onerror = (error) => {
                reject(error);
            };
        });
    };

    async function handleImageSelect(event: Event) {
        const target = event.target as HTMLInputElement;
        if (target.files && target.files[0]) {
            const file = target.files[0];
            imageBase64 = await convertBase64(file);
        }
    }

    async function handleSubmit() {
        // Updated payload
        const recipeData: Recipe = { 
            name, 
            ingredients, 
            instructions,
            rating,
            notes,
            image: imageBase64
        };

        try {
            if (editingRecipeId) {
                await updateRecipe(editingRecipeId, recipeData);
            } else {
                await createRecipe(recipeData);
            }
            resetForm();
            loadData();
        } catch (e) {
            console.error(e);
            alert('Napaka pri shranjevanju.');
        }
    }

    // ... selectIngredient, handleUpload, handleDelete remain the same ...
    function selectIngredient(itemName: string) {
        const line = `100g ${itemName}`;
        ingredients = ingredients ? `${ingredients}\n${line}` : line;
        showIngredientModal = false;
        ingredientSearch = '';
    }

    async function handleUpload() {
		const files = fileInput.getFiles();
		for (const file of files) {
			try { await uploadMarkdown(file); } catch (e) { console.error(e); alert('Napaka pri nalaganju'); }
		}
		loadData();
	}

    async function handleDelete(id: number | undefined) {
		if (!id || !confirm('Ali ste prepričani?')) return;
		try { await deleteRecipe(id); loadData(); } catch (e) { console.error(e); }
	}

    function handleEdit(recipe: Recipe) {
        editingRecipeId = recipe.id!;
        name = recipe.name;
        ingredients = recipe.ingredients;
        instructions = recipe.instructions;
        // Map new fields
        rating = recipe.rating || 0;
        notes = recipe.notes || '';
        imageBase64 = recipe.image || '';
        
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    function resetForm() {
        editingRecipeId = null;
        name = '';
        ingredients = '';
        instructions = '';
        rating = 0;
        notes = '';
        imageBase64 = '';
        if (imageInput) imageInput.value = ''; // Reset file input
    }
</script>

<main>
    <h1>Knjiga Receptov Yummerz</h1>

    <!-- Settings Bar za stranko -->
    <div class="settings-bar">
        <label>
            🎯 Ciljni dnevni vnos (kcal):
            <input type="number" bind:value={dailyTargetKcal} min="1000" step="100" style="width: 100px; display: inline-block; padding: 5px;">
        </label>
        
        <!-- BONUS: Register/Login Link if using this page as main entry -->
        <span style="margin-left: 20px;">
            <a href="/login" class="button-link" style="margin:0">Odjava / Prijava</a>
        </span>
    </div>

    <div class="form-container">
        <h2>{editingRecipeId ? 'Uredi Recept' : 'Dodaj Nov Recept'}</h2>

        <form on:submit|preventDefault={handleSubmit}>
            <!-- Image Upload -->
            <div class="form-group">
                <label for="img">Slika recepta:</label>
                <input type="file" id="img" accept="image/*" on:change={handleImageSelect} bind:this={imageInput} />
                {#if imageBase64}
                    <img src={imageBase64} alt="Preview" style="max-height: 200px; margin-top: 10px; border-radius: 8px;" />
                    <button type="button" class="secondary-outline" on:click={() => {imageBase64 = ''; imageInput.value = '';}}>Odstrani sliko</button>
                {/if}
            </div>

            <div class="form-group">
                <label for="name">Ime recepta:</label>
                <input id="name" type="text" bind:value={name} required />
            </div>

            <!-- Rating -->
            <div class="form-group">
                <label for="rating">Ocena (1-5):</label>
                <div class="rating-input">
                    <input type="range" id="rating" min="0" max="5" step="1" bind:value={rating} />
                    <span style="font-size: 1.2rem; font-weight: bold; margin-left: 10px;">{rating > 0 ? '⭐'.repeat(rating) : 'Brez ocene'}</span>
                </div>
            </div>

            <div class="form-group">
                <label for="ingredients">Sestavine (format: "200g Moka"):</label>
                <textarea id="ingredients" bind:value={ingredients} rows="4" placeholder="200g Moka&#10;150g Sladkor"></textarea>
                <button type="button" class="secondary-outline" on:click={() => (showIngredientModal = true)}>
                    + Izberi sestavino s seznama
                </button>
                <a href="/ingredients" class="button-link">Dodaj novo sestavino v shrambo</a>
            </div>

            <div class="form-group">
                <label for="instructions">Navodila:</label>
                <textarea id="instructions" bind:value={instructions} rows="6" required></textarea>
            </div>

            <!-- Notes -->
            <div class="form-group">
                <label for="notes">Moji zapiski (opombe):</label>
                <textarea id="notes" bind:value={notes} rows="3" placeholder="Zapiski o peki, spremembe..."></textarea>
            </div>

            <div class="button-group">
                <button type="submit" class="primary">
                    {editingRecipeId ? 'Shrani Spremembe' : 'Dodaj Recept'}
                </button>

                {#if editingRecipeId}
                    <button type="button" on:click={resetForm}>Prekliči</button>
                {/if}
            </div>
        </form>
        <br />
        <p style="font-size: 0.9rem; color: #666; text-align:center;">Uvozite recept iz Markdown datoteke:</p>
        <FileUpload bind:this={fileInput} on:change={handleUpload}></FileUpload>
    </div>

    <hr />

    <div class="recipes-list">
        <h2>Vsi Recepti</h2>
        <!-- Search container remains same -->
        <div class="search-container">
			<input id="search" type="text" bind:value={searchTerm} placeholder="Išči po imenu, sestavinah..." />
			<div style="margin-top: 10px;">
				<label>Skaliraj sestavine (x): <input type="number" min="0.5" step="0.5" bind:value={kolicina} style="width: 60px;" /></label>
			</div>
		</div>

        {#if recipes.length === 0}
            <p>Trenutno ni nobenih receptov.</p>
        {:else}
            {#each recipes as recipe (recipe.id)}
                {@const nutrition = calculateNutrition(recipe.ingredients)}
                <article class="recipe-card">
                    {#if recipe.image}
                        <img src={recipe.image} alt={recipe.name} class="recipe-cover-image" />
                    {/if}

                    <div class="card-header-flex">
                        <div>
                            <h3>{recipe.name}</h3>
                            {#if recipe.rating}
                                <div class="rating-display">{'⭐'.repeat(recipe.rating)}</div>
                            {/if}
                        </div>
                        
                        {#if nutrition.total > 0}
                            <div class="nutrition-badge" title="Delež dnevnega vnosa">
                                <span class="calories">🔥 {nutrition.total} kcal</span>
                                <span class="percent">({nutrition.percent}% dnevnega vnosa)</span>
                            </div>
                        {/if}
                    </div>

                    <h4>Sestavine</h4>
                    <p>{scaleIngredients(recipe.ingredients, kolicina)}</p>
                    
                    <h4>Navodila</h4>
                    <p>{recipe.instructions}</p>
                    
                    {#if recipe.notes}
                        <div class="recipe-notes">
                            <strong>📝 Zapiski:</strong>
                            <p>{recipe.notes}</p>
                        </div>
                    {/if}

                    <div class="recipe-actions">
                        <button on:click={() => handleEdit(recipe)}>Uredi</button>
                        <button class="danger" on:click={() => handleDelete(recipe.id)}>Izbriši</button>
                    </div>
                </article>
            {/each}
        {/if}
    </div>
    
    <!-- Ingredient Modal remains same -->
    {#if showIngredientModal}
        <!-- ... (modal code from original file) -->
        <div class="modal-overlay" on:click|self={() => (showIngredientModal = false)}>
			<div class="modal-content">
				<h3>Izberi sestavino</h3>
				<input type="text" placeholder="Išči sestavine..." bind:value={ingredientSearch} autofocus />
				<ul class="ingredient-select-list">
					{#each filteredIngredientsList as item}
						<li>
							<button type="button" on:click={() => selectIngredient(item.name)}>
								{item.name} ({item.calories} kcal/100g)
							</button>
						</li>
					{/each}
					{#if filteredIngredientsList.length === 0}
						<li>Ni zadetkov. <a href="/ingredients">Dodaj novo?</a></li>
					{/if}
				</ul>
				<button type="button" class="close-btn" on:click={() => (showIngredientModal = false)}>Zapri</button>
			</div>
		</div>
    {/if}
</main>

<style>
    /* ... Keep existing styles ... */
    :root {
		font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
		--primary-color: #4caf50;
		--danger-color: #f44336;
		--card-bg: #f9f9f9;
		--border-color: #ddd;
	}

	main {
		max-width: 800px;
		margin: 2rem auto;
		padding: 1rem;
	}
	
	.settings-bar {
		background: #e8f5e9;
		padding: 10px;
		border-radius: 8px;
		margin-bottom: 20px;
		text-align: center;
		border: 1px solid #c8e6c9;
	}

	h1, h2 { text-align: center; color: #333; }
	hr { margin: 2rem 0; }

	.form-container {
		background: var(--card-bg);
		padding: 2rem;
		border-radius: 8px;
		border: 1px solid var(--border-color);
	}

	.form-group { margin-bottom: 1rem; }
	.form-group label { display: block; margin-bottom: 0.5rem; font-weight: bold; }
	
	input, textarea {
		width: 100%;
		padding: 0.75rem;
		border: 1px solid var(--border-color);
		border-radius: 4px;
		box-sizing: border-box;
	}

	.button-link {
		text-decoration: none;
		display: inline-block;
		margin-top: 0.5rem;
		color: var(--primary-color);
		font-weight: bold;
		font-size: 0.9rem;
	}

	.secondary-outline {
		background: transparent;
		border: 1px solid var(--primary-color);
		color: var(--primary-color);
		margin-top: 0.5rem;
		width: 100%;
	}

	.button-group { display: flex; gap: 1rem; margin-top: 1.5rem; }

	button {
		padding: 0.75rem 1.5rem;
		border: none;
		border-radius: 4px;
		cursor: pointer;
		font-weight: bold;
	}
	button.primary { background-color: var(--primary-color); color: white; }
	button.danger { background-color: var(--danger-color); color: white; }

	.recipes-list { margin-top: 2rem; }
	.search-container { margin-bottom: 1.5rem; text-align: center; }
	.search-container input[type="text"] { max-width: 400px; display: inline-block; }

	.recipe-card {
		background: var(--card-bg);
		border: 1px solid var(--border-color);
		border-radius: 8px;
		padding: 1.5rem;
		margin-bottom: 1rem;
		position: relative;
        overflow: hidden;
	}
	
	.card-header-flex {
		display: flex;
		justify-content: space-between;
		align-items: flex-start;
		flex-wrap: wrap;
		gap: 10px;
	}
	
	.card-header-flex h3 { margin: 0; }

	/* NUTRITION BADGE STYLES */
	.nutrition-badge {
		background: #fff3e0;
		border: 1px solid #ffe0b2;
		color: #e65100;
		padding: 5px 10px;
		border-radius: 20px;
		font-size: 0.9rem;
		display: flex;
		flex-direction: column;
		align-items: flex-end;
	}
	.nutrition-badge .calories { font-weight: bold; }
	.nutrition-badge .percent { font-size: 0.75rem; opacity: 0.9; }

	.recipe-card p { white-space: pre-wrap; }
	.recipe-actions { margin-top: 1rem; display: flex; gap: 1rem; }

	/* Modal */
	.modal-overlay {
		position: fixed; top: 0; left: 0; width: 100%; height: 100%;
		background: rgba(0, 0, 0, 0.5);
		display: flex; justify-content: center; align-items: center; z-index: 1000;
	}
	.modal-content {
		background: white; padding: 2rem; border-radius: 8px;
		width: 90%; max-width: 400px; max-height: 80vh; overflow-y: auto;
	}
	.ingredient-select-list { list-style: none; padding: 0; margin: 1rem 0; }
	.ingredient-select-list li button {
		width: 100%; text-align: left; background: none;
		border-bottom: 1px solid #eee; padding: 0.5rem;
	}
	.ingredient-select-list li button:hover { background: #f0f0f0; }
	.close-btn { width: 100%; background: #ccc; margin-top: 1rem; }

    /* NEW STYLES */
    .recipe-cover-image {
        width: 100%;
        height: 200px;
        object-fit: cover;
        border-radius: 8px;
        margin-bottom: 1rem;
    }

    .rating-display {
        font-size: 1.2rem;
        margin-top: 0.2rem;
    }

    .recipe-notes {
        background-color: #fff9c4;
        padding: 10px;
        border-left: 4px solid #fbc02d;
        margin: 10px 0;
        border-radius: 4px;
    }
</style>
