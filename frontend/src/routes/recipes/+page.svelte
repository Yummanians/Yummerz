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

	let recipes: Recipe[] = [];
	let name = '';
	let ingredients = '';
	let instructions = '';
	let editingRecipeId: number | null = null;
	let searchTerm = '';
	let fileInput: any;
	let kolicina = 1;

	// Logika za izbiro sestavin
	let showIngredientModal = false;
	let ingredientSearch = '';
	// Seznam vseh sestavin - uncomenti ko bo backend
	let allIngredients = ['Moka', 'Sladkor', 'Jajca', 'Mleko', 'Maslo', 'Sol', 'Olje', 'Kvas', 'Poper'];
	async function loadIngredients() {
		try {
			allIngredients = await fetchIngredients(searchTerm);
		} catch (e) {
			console.error(e);
		}
	}
	$: filteredIngredients = allIngredients.filter((i) =>
		i.toLowerCase().includes(ingredientSearch.toLowerCase())
	);

	function scaleIngredients(text: string, multiplier: number): string {
		if (!multiplier || multiplier === 1) return text;
		return text.replace(/\b\d+(\.\d+)?\b/g, (match) =>
			(parseFloat(match) * multiplier).toString()
		);
	}

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

	function selectIngredient(item: string) {
		ingredients = ingredients ? `${ingredients}\n${item}` : item;
		showIngredientModal = false;
		ingredientSearch = '';
	}

	async function handleUpload() {
		const files = fileInput.getFiles();
		for (const file of files) {
			try {
				await uploadMarkdown(file);
			} catch (e) {
				console.error(e);
				alert('Napaka pri nalaganju');
			}
		}
		loadRecipes();
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
				<textarea id="ingredients" bind:value={ingredients} rows="4"></textarea>
				<button type="button" class="secondary-outline" on:click={() => (showIngredientModal = true)}>
					+ Izberi sestavino s seznama
				</button>
				<a href="/ingredients" class="button-link">Dodaj novo sestavino</a>
			</div>
			<div class="form-group">
				<label for="instructions">Navodila:</label>
				<textarea id="instructions" bind:value={instructions} rows="6" required></textarea>
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

		<FileUpload bind:this={fileInput} on:change={handleUpload}></FileUpload>
	</div>

	<hr />

	<div class="recipes-list">
		<h2>Vsi Recepti</h2>

		<div class="search-container">
			<input
				id="search"
				type="text"
				bind:value={searchTerm}
				placeholder="Išči po imenu, sestavinah..."
			/>
			<br />
			<h3>Količina obrokov:</h3>
			<input type="number" min="1" step="1" bind:value={kolicina} />
		</div>

		{#if recipes.length === 0}
			<p>
				{searchTerm
					? 'Noben recept ne ustreza iskanju.'
					: 'Trenutno ni nobenih receptov. Dodajte prvega!'}
			</p>
		{:else}
			{#each recipes as recipe (recipe.id)}
				<article class="recipe-card">
					<h3>{recipe.name}</h3>
					<h4>Sestavine</h4>
					<p>{scaleIngredients(recipe.ingredients, kolicina)}</p>
					<h4>Navodila</h4>
					<p>{recipe.instructions}</p>
					<div class="recipe-actions">
						<button on:click={() => handleEdit(recipe)}>Uredi</button>
						<button class="danger" on:click={() => handleDelete(recipe.id)}>Izbriši</button>
					</div>
				</article>
			{/each}
		{/if}
	</div>

	{#if showIngredientModal}
		<div class="modal-overlay" on:click|self={() => (showIngredientModal = false)}>
			<div class="modal-content">
				<h3>Izberi sestavino</h3>
				<input
					type="text"
					placeholder="Išči sestavine..."
					bind:value={ingredientSearch}
					autofocus
				/>
				<ul class="ingredient-select-list">
					{#each filteredIngredients as item}
						<li>
							<button type="button" on:click={() => selectIngredient(item)}>
								{item}
							</button>
						</li>
					{/each}
					{#if filteredIngredients.length === 0}
						<li>Ni zadetkov...</li>
					{/if}
				</ul>
				<button type="button" class="close-btn" on:click={() => (showIngredientModal = false)}>
					Zapri
				</button>
			</div>
		</div>
	{/if}
</main>

<style>
	:root {
		font-family:
			-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans',
			'Helvetica Neue', sans-serif;
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

	h1,
	h2 {
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
	
	input,
	textarea {
		width: 100%;
		padding: 0.75rem;
		border: 1px solid var(--border-color);
		border-radius: 4px;
		font-size: 1rem;
		box-sizing: border-box;
	}
	.button-link {
    text-decoration: none;
    
    display: block;         
    width: fit-content;      
    margin: 0.5rem auto;      
    
    background-color: var(--primary-color);
    color: white;
    border-radius: 4px;
    font-weight: bold;
    transition: background-color 0.2s;
	}

	.button-link:hover {
		background-color: #45a049;
		color: white; 
	}

	.secondary-outline {
		background: transparent;
		border: 1px solid var(--primary-color);
		color: var(--primary-color);
		margin-top: 0.5rem;
		width: 100%;
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

	/* Modalni slogi */
	.modal-overlay {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background: rgba(0, 0, 0, 0.5);
		display: flex;
		justify-content: center;
		align-items: center;
		z-index: 1000;
	}

	.modal-content {
		background: white;
		padding: 2rem;
		border-radius: 8px;
		width: 90%;
		max-width: 400px;
		max-height: 80vh;
		overflow-y: auto;
	}

	.ingredient-select-list {
		list-style: none;
		padding: 0;
		margin: 1rem 0;
	}

	.ingredient-select-list li button {
		width: 100%;
		text-align: left;
		background: none;
		color: #333;
		border-bottom: 1px solid #eee;
		border-radius: 0;
		padding: 0.5rem;
	}

	.ingredient-select-list li button:hover {
		background: #f0f0f0;
	}

	.close-btn {
		width: 100%;
		background: #ccc;
		margin-top: 1rem;
	}
</style>