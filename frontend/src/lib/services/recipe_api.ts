const API_URL = 'http://localhost:8080/api/recipes';

export interface Recipe {
    id?: number;
    name: string;
    ingredients: string;
    instructions: string;
}

export async function fetchRecipes(searchTerm: string = ''): Promise<Recipe[]> {
    const url = searchTerm ? `${API_URL}?search=${encodeURIComponent(searchTerm)}` : API_URL;
    const response = await fetch(url);
    if (!response.ok) throw new Error('Failed to fetch recipes');
    return await response.json();
}

export async function createRecipe(recipe: Recipe): Promise<void> {
    const response = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(recipe)
    });
    if (!response.ok) throw new Error('Failed to create recipe');
}

export async function updateRecipe(id: number, recipe: Recipe): Promise<void> {
    const response = await fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(recipe)
    });
    if (!response.ok) throw new Error('Failed to update recipe');
}

export async function deleteRecipe(id: number): Promise<void> {
    const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
    if (!response.ok) throw new Error('Failed to delete recipe');
}

export async function uploadMarkdown(file: File): Promise<void> {
    const formData = new FormData();
    formData.append('file', file);
    
    const response = await fetch(`${API_URL}/import`, {
        method: 'POST',
        body: formData
    });
    if (!response.ok) throw new Error('Failed to upload markdown');
}
