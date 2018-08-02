package com.example.snackbar.snackbar.promotion;

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
@PrepareForTest({PromotionService.class})
@Category(FailFast.class)
public class PromotionServiceTest {

    @Mock
    private PromotionService promotionService;

    @Mock
    private PromotionRepository promotionRepository;

    private PromotionService promotionServiceSpy;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        promotionService = new PromotionService(promotionRepository);
        promotionServiceSpy = PowerMockito.spy(promotionService);
    }

    @Test
    public void shouldNotFindAllSnacks() {
        when(promotionServiceSpy.getAllPromotion()).thenReturn(null);
        assertNull(promotionServiceSpy.getAllPromotion());
    }

    @Test
    public void shouldFindAllSnacks() {
        List<Promotion> promotionList = new ArrayList<>();
        promotionList.add(new Promotion("Prom1", null, null, 1,10.0, false));
        promotionList.add(new Promotion("Prom2", null, null, 1,15.0, true));

        when(promotionServiceSpy.getAllPromotion()).thenReturn(promotionList);
        assertEquals(promotionServiceSpy.getAllPromotion(), promotionList);
    }

    @Test
    public void shouldNotExistSnack_whenWithoutIdInvalid() {
        when(promotionServiceSpy.exists("XPTO2")).thenReturn(false);
        assertFalse(promotionServiceSpy.exists("XPTO1"));
    }

    @Test
    public void shouldExistSnack_whenWithIdValid() {
        when(promotionServiceSpy.exists("XPTO")).thenReturn(true);
        assertTrue(promotionServiceSpy.exists("XPTO"));
    }

    @Test
    public void shouldNotFindSnack_whenWithoutId() {
        when(promotionServiceSpy.findById("")).thenReturn(null);
        assertNull(promotionServiceSpy.findById(""));
    }

    @Test
    public void shouldFindSnack_whenWithId() {
        Promotion promotion = new Promotion("Prom1", null, null,1, 10.0, false);
        when(promotionServiceSpy.findById("123")).thenReturn(promotion);
        assertEquals(promotionServiceSpy.findById("123"), promotion);
    }

}
