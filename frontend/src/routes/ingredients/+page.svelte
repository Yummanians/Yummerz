<script lang="ts">
	import { onMount } from 'svelte';
	import {
		fetchIngredients,
		createIngredient,
		updateIngredient,
		deleteIngredient,
		type Recipe,

	} from '$lib/services/recipe_api';
	import '$lib/styles/global.css';

	interface Ingredient {
		id: number;
		name: string;
		imageUrl: string;
		allergens: string[];
		calories: number;
		protein: number;
		carbs: number;
		fat: number;
	}

	let ingredients: Ingredient[] = [];
	let searchTerm = '';
	let editingId: number | null = null;

	let name = '';
	let imageUrl = '';
	let allergensInput = '';
	let calories: number = 0;
	let protein: number = 0;
	let carbs: number = 0;
	let fat: number = 0;


	onMount(fetchIngredients);

	$: if (searchTerm !== undefined) fetchIngredients();

	async function handleSubmit() {
		const allergenArray = allergensInput
			.split(',')
			.map((s) => s.trim())
			.filter((s) => s !== '');

		const ingredientData = { 
			name, 
			imageUrl: imageUrl || 'https://via.placeholder.com/150?text=Sestavina', 
			allergens: allergenArray, 
			calories, 
			protein, 
			carbs, 
			fat 
		};

		try {
			if (editingId) {
				console.log('Updating ingredient:', editingId, ingredientData);
				await updateIngredient(editingId, ingredientData);
			} else {
				console.log('Creating ingredient:', ingredientData);
				await createIngredient(ingredientData);
			}
			resetForm();
			fetchIngredients();
		} catch (e) {
			alert('Napaka pri shranjevanju sestavine.');
		}
	}

	function handleEdit(item: Ingredient) {
		editingId = item.id;
		name = item.name;
		imageUrl = item.imageUrl;
		allergensInput = item.allergens.join(', ');
		calories = item.calories;
		protein = item.protein;
		carbs = item.carbs;
		fat = item.fat;
		window.scrollTo({ top: 0, behavior: 'smooth' });
	}

	async function handleDelete(id: number) {
		if (!confirm('Ali res želite izbrisati to sestavino?')) return;
		console.log('Deleting:', id);
		await deleteIngredient(id);
		fetchIngredients();
	}

	function resetForm() {
		editingId = null;
		name = '';
		imageUrl = '';
		allergensInput = '';
		calories = 0;
		protein = 0;
		carbs = 0;
		fat = 0;
	}
</script>

<main>
	<h1>Shramba Sestavin Yummerz</h1>

	<div class="form-container">
		<h2>{editingId ? 'Uredi Sestavino' : 'Dodaj Novo Sestavino'}</h2>

		<form on:submit|preventDefault={handleSubmit}>
			<div class="form-grid">
				<div class="form-group">
					<label for="name">Ime sestavine:</label>
					<input id="name" type="text" bind:value={name} placeholder="npr. Piščanec" required />
				</div>
				<div class="form-group">
					<label for="image">URL Slike / Ikone:</label>
					<input id="image" type="text" bind:value={imageUrl} placeholder="https://..." />
				</div>
			</div>

			<div class="form-group">
				<label for="allergens">Alergeni (ločite z vejico):</label>
				<input id="allergens" type="text" bind:value={allergensInput} placeholder="Laktosa, Oreščki, Gluten..." />
			</div>

			<div class="nutrition-grid-inputs">
				<div class="form-group">
					<label for="kcal">Kcal (100g)</label>
					<input id="kcal" type="number" bind:value={calories} />
				</div>
				<div class="form-group">
					<label for="prot">B (g)</label>
					<input id="prot" type="number" bind:value={protein} />
				</div>
				<div class="form-group">
					<label for="carb">OH (g)</label>
					<input id="carb" type="number" bind:value={carbs} />
				</div>
				<div class="form-group">
					<label for="fat">M (g)</label>
					<input id="fat" type="number" bind:value={fat} />
				</div>
			</div>

			<div class="button-group">
				<button type="submit" class="primary">
					{editingId ? 'Shrani Spremembe' : 'Dodaj v Shrambo'}
				</button>
				{#if editingId}
					<button type="button" on:click={resetForm}>Prekliči</button>
				{/if}
			</div>
		</form>
	</div>

	<hr />

	<div class="ingredients-list">
		<h2>Seznam Sestavin</h2>

		<div class="search-container">
			<input type="text" bind:value={searchTerm} placeholder="Išči sestavine..." />
		</div>

		{#if ingredients.length === 0}
			<p class="empty-msg">Ni najdenih sestavin. Dodajte svojo prvo sestavino zgoraj!</p>
		{:else}
			<div class="grid-display">
				{#each ingredients as item (item.id)}
					<article class="ingredient-card">
						<div class="card-header">
							<img src={item.imageUrl} alt={item.name} class="ingredient-icon" />
							<h3>{item.name}</h3>
						</div>

						{#if item.allergens.length > 0}
							<div class="allergen-tags">
								{#each item.allergens as allergen}
									<span class="tag">! {allergen}</span>
								{/each}
							</div>
						{/if}

						<div class="nutrition-panel">
							<div class="nutri-row">
								<span>Kalorije</span>
								<strong>{item.calories} kcal</strong>
							</div>
							<div class="nutri-row sub">
								<span>Beljakovine</span>
								<span>{item.protein}g</span>
							</div>
							<div class="nutri-row sub">
								<span>Ogljikovi hidrati</span>
								<span>{item.carbs}g</span>
							</div>
							<div class="nutri-row sub">
								<span>Maščobe</span>
								<span>{item.fat}g</span>
							</div>
						</div>

						<div class="recipe-actions">
							<button on:click={() => handleEdit(item)}>Uredi</button>
							<button class="danger" on:click={() => handleDelete(item.id)}>Izbriši</button>
						</div>
					</article>
				{/each}
			</div>
		{/if}
	</div>
</main>

<style>
	:root {
		--primary-color: #4caf50;
		--danger-color: #f44336;
		--card-bg: #f9f9f9;
		--border-color: #ddd;
	}

	main {
		max-width: 900px;
		margin: 2rem auto;
		padding: 1rem;
		font-family: sans-serif;
	}

	h1, h2 {
		text-align: center;
		color: #333;
	}

	.form-container {
		background: var(--card-bg);
		padding: 2rem;
		border-radius: 8px;
		border: 1px solid var(--border-color);
		margin-bottom: 2rem;
	}

	.form-grid {
		display: grid;
		grid-template-columns: 1fr 1fr;
		gap: 1rem;
	}

	.nutrition-grid-inputs {
		display: grid;
		grid-template-columns: repeat(4, 1fr);
		gap: 10px;
		margin-top: 1rem;
	}

	.form-group {
		margin-bottom: 1rem;
	}

	.form-group label {
		display: block;
		margin-bottom: 0.4rem;
		font-weight: bold;
		font-size: 0.9rem;
	}

	input {
		width: 100%;
		padding: 0.75rem;
		border: 1px solid var(--border-color);
		border-radius: 4px;
		box-sizing: border-box;
	}

	.button-group {
		display: flex;
		gap: 1rem;
		margin-top: 1rem;
	}

	button {
		padding: 0.75rem 1.5rem;
		border: none;
		border-radius: 4px;
		cursor: pointer;
		font-weight: bold;
	}

	button.primary { background: var(--primary-color); color: white; }
	button.danger { background: var(--danger-color); color: white; }

	.search-container {
		margin-bottom: 2rem;
		text-align: center;
	}

	.search-container input {
		max-width: 400px;
	}

	.grid-display {
		display: grid;
		grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
		gap: 1.5rem;
	}

	.ingredient-card {
		background: white;
		border: 1px solid var(--border-color);
		border-radius: 12px;
		padding: 1.25rem;
		display: flex;
		flex-direction: column;
		box-shadow: 0 2px 5px rgba(0,0,0,0.05);
	}

	.card-header {
		display: flex;
		align-items: center;
		gap: 1rem;
		margin-bottom: 1rem;
	}

	.ingredient-icon {
		width: 60px;
		height: 60px;
		border-radius: 50%;
		object-fit: cover;
		background: #eee;
	}

	.allergen-tags {
		display: flex;
		flex-wrap: wrap;
		gap: 4px;
		margin-bottom: 1rem;
	}

	.tag {
		background: #fff0f0;
		color: #d32f2f;
		border: 1px solid #ffcdd2;
		padding: 2px 8px;
		border-radius: 20px;
		font-size: 0.75rem;
		font-weight: bold;
	}

	.nutrition-panel {
		background: #f8f9fa;
		padding: 0.75rem;
		border-radius: 8px;
		margin-bottom: 1rem;
	}

	.nutri-row {
		display: flex;
		justify-content: space-between;
		font-size: 0.9rem;
		padding: 4px 0;
	}

	.nutri-row.sub {
		border-top: 1px solid #eee;
		color: #666;
	}

	.recipe-actions {
		margin-top: auto;
		display: flex;
		gap: 0.5rem;
	}

	.empty-msg {
		text-align: center;
		color: #666;
	}
</style>