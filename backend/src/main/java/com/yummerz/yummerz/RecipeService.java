package com.yummerz.yummerz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipes(Optional<String> searchTerm) {
        if (searchTerm.isPresent() && !searchTerm.get().isBlank()) {
            String query = searchTerm.get();
            return recipeRepository.findByNameContainingIgnoreCaseOrIngredientsContainingIgnoreCaseOrInstructionsContainingIgnoreCase(query, query, query);
        } else {
            return recipeRepository.findAll();
        }
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public Optional<Recipe> updateRecipe(Long id, Recipe recipeDetails) {
        return recipeRepository.findById(id)
                .map(existingRecipe -> {
                    existingRecipe.setName(recipeDetails.getName());
                    existingRecipe.setIngredients(recipeDetails.getIngredients());
                    existingRecipe.setInstructions(recipeDetails.getInstructions());
                    return recipeRepository.save(existingRecipe);
                });
    }

    public boolean deleteRecipe(Long id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
   
    public void importRecipeFromMarkdown(MultipartFile file) throws IOException {
        String name = "Untitled Recipe";
        StringBuilder ingredients = new StringBuilder();
        StringBuilder instructions = new StringBuilder();
        
        String currentSection = ""; 

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                if (line.startsWith("# ")) {
                    name = line.substring(2).trim();
                } else if (line.startsWith("## Ingredients")) {
                    currentSection = "INGREDIENTS";
                } else if (line.startsWith("## Instructions") || line.startsWith("## Preparation")) {
                    currentSection = "INSTRUCTIONS";
                } else if (!line.isEmpty()) {
                    if ("INGREDIENTS".equals(currentSection)) {
                        ingredients.append(line).append("\n");
                    } else if ("INSTRUCTIONS".equals(currentSection)) {
                        instructions.append(line).append("\n");
                    }
                }
            }
        }

        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setIngredients(ingredients.toString().trim());
        recipe.setInstructions(instructions.toString().trim());
        
        recipeRepository.save(recipe);
    }
}
