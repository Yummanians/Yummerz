import { get } from 'svelte/store';
import { token } from '../authStore';

const BASE_URL = 'http://localhost:8080/api/recipes';

export interface Recipe {
	id?: number;
	name: string;
	ingredients: string;
	instructions: string;
	image?: string;
	rating?: number;
	notes?: string;
}

export interface Ingredient {
	id?: number;
	name: string;
	imageUrl: string;
	allergens: string[];

	calories: number;
	protein: number;
	carbs: number;
	fat: number;
}

async function apiRequest(url: string, method = 'GET', body?: any) {
	const jwt = get(token); // Get token from store
	const headers: Record<string, string> = {
		'Content-Type': 'application/json'
	};
	if (jwt) headers['Authorization'] = `Bearer ${jwt}`;

	const res = await fetch(url, {
		method,
		headers,
		body: body ? JSON.stringify(body) : undefined
	});

	if (res.status === 403 || res.status === 401) {
		token.set(null); // Clear token if expired/unauthorized
		throw new Error('Unauthorized');
	}
	return res;
}

export async function fetchRecipes(search = ''): Promise<Recipe[]> {
	const res = await apiRequest(`${BASE_URL}?search=${search}`);
	return res.json();
}

export async function fetchIngredients(search = ''): Promise<Recipe[]> {
	const res = await apiRequest(`${BASE_URL}?search=${search}`);
	return res.json();
}

export async function createRecipe(recipe: Recipe) {
	const res = await apiRequest(BASE_URL, 'POST', recipe);
	return res.json();
}

export async function updateRecipe(id: number, recipe: Recipe) {
	const res = await apiRequest(`${BASE_URL}/${id}`, 'PUT', recipe);
	return res.json();
}

export async function createIngredient(ingredient: Ingredient) {
	const res = await apiRequest(BASE_URL, 'POST', ingredient);
	return res.json();
}

export async function updateIngredient(id: number, ingredient: Ingredient) {
	const res = await apiRequest(`${BASE_URL}/${id}`, 'PUT', ingredient);
	return res.json();
}

export async function deleteIngredient(id: number) {
	return apiRequest(`${BASE_URL}/${id}`, 'DELETE');
}

export async function deleteRecipe(id: number) {
	return apiRequest(`${BASE_URL}/${id}`, 'DELETE');
}

export async function uploadMarkdown(file: File) {
	const jwt = get(token);
	const formData = new FormData();
	formData.append('file', file);

	const res = await fetch(`${BASE_URL}/import`, {
		method: 'POST',
		headers: { Authorization: `Bearer ${jwt}` },
		body: formData
	});
	return res;
}

