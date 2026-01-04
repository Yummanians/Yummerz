package com.yummerz.yummerz;

import com.yummerz.yummerz.recipe.Recipe;
import com.yummerz.yummerz.recipe.RecipeRepository;
import com.yummerz.yummerz.recipe.RecipeService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private RecipeService recipeService;

    // Ta metoda se izvede pred vsakim testom in nastavi "lažnega" uporabnika
    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.getName()).thenReturn("testUser");
    }

    // Po testu počistimo kontekst, da ne vplivamo na druge teste
    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Gets recipes for current owner")
    void getAllRecipes_NoSearchTerm_ReturnsAllForOwner() {
        Recipe r1 = new Recipe(); r1.setName("Palačinke"); r1.setOwner("testUser");
        Recipe r2 = new Recipe(); r2.setName("Pizza"); r2.setOwner("testUser");
        
        // POPRAVEK: Service zdaj kliče findAllByOwner, ne findAll
        when(recipeRepository.findAllByOwner("testUser")).thenReturn(Arrays.asList(r1, r2));

        List<Recipe> result = recipeService.getAllRecipes(Optional.empty());

        Assertions.assertEquals(2, result.size());
        // Preverimo, da se je klicala pravilna metoda repozitorija
        verify(recipeRepository, times(1)).findAllByOwner("testUser");
    }

    @Test
    @DisplayName("Mora vrniti filtrirane recepte, ko je podan iskalni niz")
    void getAllRecipes_WithSearchTerm_ReturnsFiltered() {
        String query = "Test";
        Recipe r1 = new Recipe(); r1.setName("Test Recipe");
        
        // POPRAVEK: Service kliče searchMyRecipes, ki sprejme query in ownerja
        when(recipeRepository.searchMyRecipes(query, "testUser"))
                .thenReturn(List.of(r1));

        List<Recipe> result = recipeService.getAllRecipes(Optional.of("Test"));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Test Recipe", result.get(0).getName());
    }

    @Test
    @DisplayName("Markdown import success")
    void importRecipeFromMarkdown_ValidFile_SavesRecipe() throws IOException {
        String markdownContent = "# Super Torta\n\n## Ingredients\n- Moka\n- Sladkor\n\n## Instructions\nZmešaj in peci.";
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.md",
                "text/markdown",
                markdownContent.getBytes()
        );

        recipeService.importRecipeFromMarkdown(file);

        verify(recipeRepository, times(1)).save(argThat(recipe ->
                recipe.getName().equals("Super Torta") &&
                recipe.getIngredients().contains("Moka") &&
                recipe.getInstructions().equals("Zmešaj in peci.") &&
                recipe.getOwner().equals("testUser") // Preverimo še lastnika
        ));
    }

    @Test
    @DisplayName("returns empty on non existing id (bad)")
    void updateRecipe_NonExistentId_ReturnsEmpty() {
        Long invalidId = 99L;
        Recipe newData = new Recipe();
        when(recipeRepository.findById(invalidId)).thenReturn(Optional.empty());

        Optional<Recipe> result = recipeService.updateRecipe(invalidId, newData);

        Assertions.assertTrue(result.isEmpty());
        verify(recipeRepository, never()).save(any());
    }

    @Test
    @DisplayName("return true on delete")
    void deleteRecipe_ExistingId_ReturnsTrue() {
        Long id = 1L;
        when(recipeRepository.existsById(id)).thenReturn(true);

        boolean deleted = recipeService.deleteRecipe(id);

        Assertions.assertTrue(deleted);
        verify(recipeRepository).deleteById(id);
    }
}
