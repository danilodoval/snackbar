package com.example.snackbar.snackbar.ingredient;

import com.example.snackbar.test.FailFast;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({IngredientService.class})
@Category(FailFast.class)
public class IngredientServiceTest {

    @Mock
    private IngredientService ingredientService;

    @Mock
    private IngredientRepository ingredientRepository;

    private IngredientService ingredientServiceSpy;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientService();
        ingredientServiceSpy = PowerMockito.spy(ingredientService);
    }

    @Test
    public void shouldNotFindAllIngredients() {
        PageRequest pageRequest = new PageRequest(1, 10);
        when(ingredientServiceSpy.getAllIngredients(pageRequest)).thenReturn(null);
        assertNull(ingredientServiceSpy.getAllIngredients(pageRequest));
    }

    @Test
    public void shouldFindAllIngredients() {
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient("XBacon", 2.0));
        ingredientList.add(new Ingredient("XBurger", 1.0));

        PageRequest pageRequest = new PageRequest(1, 10);
        Page<Ingredient> page = new PageImpl<>(ingredientList);

        when(ingredientServiceSpy.getAllIngredients(pageRequest)).thenReturn(page);
        assertEquals(ingredientServiceSpy.getAllIngredients(pageRequest), page);
    }

    @Test
    public void shouldNotExistIngredient_whenWithoutIdInvalid() {
        when(ingredientServiceSpy.exists("XPTO2")).thenReturn(false);
        assertFalse(ingredientServiceSpy.exists("XPTO1"));
    }

    @Test
    public void shouldExistIngredient_whenWithIdValid() {
        when(ingredientServiceSpy.exists("XPTO")).thenReturn(true);
        assertTrue(ingredientServiceSpy.exists("XPTO"));
    }

    @Test
    public void shouldNotFindIngredient_whenWithoutId() {
        when(ingredientServiceSpy.findById("")).thenReturn(null);
        assertNull(ingredientServiceSpy.findById(""));
    }

    @Test
    public void shouldFindIngredient_whenWithId() {
        Ingredient ingredient = new Ingredient("XPTO",  1.0);
        when(ingredientServiceSpy.findById("123")).thenReturn(ingredient);
        assertEquals(ingredientServiceSpy.findById("123"), ingredient);
    }
}
