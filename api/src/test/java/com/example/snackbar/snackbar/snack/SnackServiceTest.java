package com.example.snackbar.snackbar.snack;

import com.example.snackbar.snackbar.ingredient.Ingredient;
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
@PrepareForTest({SnackService.class})
@Category(FailFast.class)
public class SnackServiceTest {

    @Mock
    private SnackService snackService;

    @Mock
    private SnackRepository snackRepository;

    private SnackService snackServiceSpy;

    private Snack snack;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        snackService = new SnackService(snackRepository);
        snackServiceSpy = PowerMockito.spy(snackService);
    }

    @Test
    public void shouldNotFindAllSnacks() {
        PageRequest pageRequest = new PageRequest(1, 10);
        when(snackServiceSpy.getAllSnacks(pageRequest)).thenReturn(null);
        assertNull(snackServiceSpy.getAllSnacks(pageRequest));
    }

    @Test
    public void shouldFindAllSnacks() {
        List<Snack> snackList = new ArrayList<>();
        snackList.add(new Snack("XBacon", null));
        snackList.add(new Snack("XBurger", null));

        PageRequest pageRequest = new PageRequest(1, 10);
        Page<Snack> page = new PageImpl<>(snackList);

        when(snackServiceSpy.getAllSnacks(pageRequest)).thenReturn(page);
        assertEquals(snackServiceSpy.getAllSnacks(pageRequest), page);
    }

    @Test
    public void shouldNotExistSnack_whenWithoutIdInvalid() {
        when(snackServiceSpy.exists("XPTO2")).thenReturn(false);
        assertFalse(snackServiceSpy.exists("XPTO1"));
    }

    @Test
    public void shouldExistSnack_whenWithIdValid() {
        when(snackServiceSpy.exists("XPTO")).thenReturn(true);
        assertTrue(snackServiceSpy.exists("XPTO"));
    }

    @Test
    public void shouldNotFindSnack_whenWithoutId() {
        when(snackServiceSpy.findById("")).thenReturn(null);
        assertNull(snackServiceSpy.findById(""));
    }

    @Test
    public void shouldFindSnack_whenWithId() {
        Snack snack = new Snack("XPTO",  null);
        when(snackServiceSpy.findById("123")).thenReturn(snack);
        assertEquals(snackServiceSpy.findById("123"), snack);
    }

    @Test
    public void shouldCalculateCost(){
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient("XBacon", 2.0));
        ingredientList.add(new Ingredient("XBurger", 1.0));
        snack = new Snack("XPTO", ingredientList);

        assertEquals(3.0, snackServiceSpy.calculateCost(snack), 0.0);
    }
}
