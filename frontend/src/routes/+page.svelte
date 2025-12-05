<script lang="ts">
    import { onMount } from 'svelte';
    import { fetchRecipes, createRecipe, updateRecipe, deleteRecipe, uploadMarkdown, type Recipe } from '$lib/services/recipe_api';
    import '$lib/styles/global.css'; 

    let recipes: Recipe[] = [];
    let name = '';
    let ingredients = '';
    let instructions = '';
    let editingRecipeId: number | null = null;
    let searchTerm = '';
    let fileInput: HTMLInputElement;

    async function loadRecipes() {
        try {
            recipes = await fetchRecipes(searchTerm);
        } catch (e) {
            console.error(e);
        }
    }

    onMount(loadRecipes);

    $: if (searchTerm !== undefined) loadRecipes();

    async function handleSubmit() {
        const recipeData = { name, ingredients, instructions };
        try {
            if (editingRecipeId) {
                await updateRecipe(editingRecipeId, recipeData);
            } else {
                await createRecipe(recipeData);
            }
            resetForm();
            loadRecipes();
        } catch (e) {
            console.error(e);
            alert('Napaka pri shranjevanju.');
        }
    }

    async function handleDelete(id: number | undefined) {
        if (!id || !confirm('Ali ste prepričani?')) return;
        try {
            await deleteRecipe(id);
            loadRecipes();
        } catch (e) {
            console.error(e);
        }
    }

    function handleEdit(recipe: Recipe) {
        editingRecipeId = recipe.id!;
        name = recipe.name;
        ingredients = recipe.ingredients;
        instructions = recipe.instructions;
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    function resetForm() {
        editingRecipeId = null;
        name = '';
        ingredients = '';
        instructions = '';
    }
</script>

<main>
    <h1>Knjiga Receptov Yummerz</h1>

    <div class="form-container">
        <h2>{editingRecipeId ? 'Uredi Recept' : 'Dodaj Nov Recept'}</h2>

        <form on:submit|preventDefault={handleSubmit}>
            <div class="form-group">
                <label for="name">Ime recepta:</label>
                <input id="name" type="text" bind:value={name} required />
            </div>
            <div class="form-group">
                <label for="ingredients">Sestavine:</label>
                <textarea id="ingredients" bind:value={ingredients} rows="4" required></textarea>
            </div>
            <div class="form-group">
                <label for="instructions">Navodila:</label>
                <textarea id="instructions" bind:value={instructions} rows="6" required></textarea>
            </div>

            <div class="button-group">
                <button type="submit" class="primary">{editingRecipeId ? 'Shrani Spremembe' : 'Dodaj Recept'}</button>

                {#if editingRecipeId}
                    <button type="button" on:click={resetForm}>Prekliči</button>
                {/if}
            </div>
        </form>
    </div>

    <hr />

    <div class="recipes-list">
        <h2>Vsi Recepti</h2>

        <div class="search-container">
            <input id="search" type="text" bind:value={searchTerm} placeholder="Išči po imenu, sestavinah..." />
        </div>

        {#if recipes.length === 0}
            <p>{searchTerm ? 'Noben recept ne ustreza iskanju.' : 'Trenutno ni nobenih receptov. Dodajte prvega!'}</p>
        {:else}
            {#each recipes as recipe (recipe.id)}
                <article class="recipe-card">
                    <h3>{recipe.name}</h3>
                    <h4>Sestavine</h4>
                    <p>{recipe.ingredients}</p>
                    <h4>Navodila</h4>
                    <p>{recipe.instructions}</p>
                    <div class="recipe-actions">
                        <button on:click={() => handleEdit(recipe)}>Uredi</button>
                        <button class="danger" on:click={() => deleteRecipe(recipe.id)}>Izbriši</button>
                    </div>
                </article>
            {/each}
        {/if}
    </div>
</main>

<style>
    :root {
        font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
        --primary-color: #4CAF50;
        --danger-color: #f44336;
        --card-bg: #f9f9f9;
        --border-color: #ddd;
    }

    main {
        max-width: 800px;
        margin: 2rem auto;
        padding: 1rem;
    }

    h1, h2 {
        text-align: center;
        color: #333;
    }

    hr {
        margin: 2rem 0;
    }

    .form-container {
        background: var(--card-bg);
        padding: 2rem;
        border-radius: 8px;
        border: 1px solid var(--border-color);
    }

    .form-group {
        margin-bottom: 1rem;
    }

    .form-group label {
        display: block;
        margin-bottom: 0.5rem;
        font-weight: bold;
    }

    input, textarea {
        width: 100%;
        padding: 0.75rem;
        border: 1px solid var(--border-color);
        border-radius: 4px;
        font-size: 1rem;
        box-sizing: border-box;
    }

    .button-group {
        display: flex;
        gap: 1rem;
        margin-top: 1.5rem;
    }

    button {
        padding: 0.75rem 1.5rem;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 1rem;
        font-weight: bold;
        transition: background-color 0.2s;
    }

    button.primary {
        background-color: var(--primary-color);
        color: white;
    }
    button.primary:hover {
        background-color: #45a049;
    }

    button.danger {
        background-color: var(--danger-color);
        color: white;
    }
    button.danger:hover {
        background-color: #da190b;
    }

    .recipes-list {
        margin-top: 2rem;
    }

    /* NOVO: Stil za iskalno polje */
    .search-container {
        margin-bottom: 1.5rem;
    }
    .search-container input {
        max-width: 400px;
        display: block;
        margin: 0 auto;
        text-align: center;
    }

    .recipe-card {
        background: var(--card-bg);
        border: 1px solid var(--border-color);
        border-radius: 8px;
        padding: 1.5rem;
        margin-bottom: 1rem;
    }

    .recipe-card h3 {
        margin-top: 0;
    }

    .recipe-card p {
        white-space: pre-wrap;
    }

    .recipe-actions {
        margin-top: 1rem;
        display: flex;
        gap: 1rem;
    }
</style>
