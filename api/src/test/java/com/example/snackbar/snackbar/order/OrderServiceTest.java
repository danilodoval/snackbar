package com.example.snackbar.snackbar.order;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import com.example.snackbar.snackbar.exception.MessageException;
import com.example.snackbar.snackbar.ingredient.Ingredient;
import com.example.snackbar.snackbar.ingredient.IngredientService;
import com.example.snackbar.snackbar.promotion.Promotion;
import com.example.snackbar.snackbar.promotion.PromotionService;
import com.example.snackbar.snackbar.snack.Snack;
import com.example.snackbar.snackbar.snack.SnackData;
import com.example.snackbar.snackbar.snack.SnackService;
import com.example.snackbar.test.FailFast;

@RunWith(PowerMockRunner.class)
@PrepareForTest({OrderService.class})
@Category(FailFast.class)
public class OrderServiceTest {

    @Mock
    private OrderService orderService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private PromotionService promotionService;

    @Mock
    private SnackService snackService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Logger logger;

    private OrderService orderServiceSpy;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderService(orderRepository, ingredientService, promotionService, snackService, logger);
        orderServiceSpy = PowerMockito.spy(orderService);
    }

    @Test
    public void shouldNotFindAllOrders() {
        PageRequest pageRequest = new PageRequest(1, 10);
        when(orderServiceSpy.getAllOrders(pageRequest)).thenReturn(null);
        assertNull(orderServiceSpy.getAllOrders(pageRequest));
    }

    @Test
    public void shouldFindAllOrders() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order("XPTO", null, 0.0, 0));
        orderList.add(new Order("XYZW", null, 0.0, 0));

        PageRequest pageRequest = new PageRequest(1, 10);
        Page<Order> page = new PageImpl<>(orderList);

        when(orderServiceSpy.getAllOrders(pageRequest)).thenReturn(page);
        assertEquals(orderServiceSpy.getAllOrders(pageRequest), page);
    }

    @Test
    public void shouldNotExistOrder_whenWithoutIdInvalid() {
        when(orderServiceSpy.exists("XPTO2")).thenReturn(false);
        assertFalse(orderServiceSpy.exists("XPTO1"));
    }

    @Test
    public void shouldExistOrder_whenWithIdValid() {
        when(orderServiceSpy.exists("XPTO")).thenReturn(true);
        assertTrue(orderServiceSpy.exists("XPTO"));
    }

    @Test
    public void shouldNotFindOrder_whenWithoutId() {
        when(orderServiceSpy.findById("")).thenReturn(null);
        assertNull(orderServiceSpy.findById(""));
    }

    @Test
    public void shouldFindOrder_whenWithId() {
        Order order = new Order("XPTO", null, 0.0, 0);
        when(orderServiceSpy.findById("123")).thenReturn(order);
        assertEquals(orderServiceSpy.findById("123"), order);
    }

    @Test(expected = MessageException.class)
    public void shouldNotCreateOrder_whenNotInformedClient() {
        OrderData orderData = new OrderData("", null);
        assertEquals(orderServiceSpy.createOrder(orderData).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected = MessageException.class)
    public void shouldNotCreateOrder_whenWithOutsnacks() {
        List<SnackData> snacks = new ArrayList<>();
        snacks.add(new SnackData("XPTO", 1));
        OrderData orderData = new OrderData("XPTO", null);


        assertEquals(orderServiceSpy.createOrder(orderData).getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test(expected = MessageException.class)
    public void shouldNotCreateOrder_whenSnackDoesNotExist() {
        List<SnackData> snacks = new ArrayList<>();
        snacks.add(new SnackData("XPTO", 1));
        OrderData orderData = new OrderData("XPTO", snacks);

        when(orderServiceSpy.findById("123")).thenReturn(null);
        assertEquals(orderServiceSpy.createOrder(orderData).getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldCreateOrder() {
        List<SnackData> snacks = new ArrayList<>();
        snacks.add(new SnackData("123", 1));
        OrderData orderData = new OrderData("XPTO", snacks);

        Snack snack = new Snack("SNACK", null);
        when(snackService.findById("123")).thenReturn(snack);

        Order order = new Order("XPTO", null, 0.0, 0);
        when(orderRepository.save(order)).thenReturn(order);
        assertEquals(orderServiceSpy.createOrder(orderData).getStatusCode(), HttpStatus.OK);
    }

    @Test(expected = MessageException.class)
    public void shouldNotCreateCustomOrder_whenNotInformedClient() {
        OrderDataCustom orderDataCustom = new OrderDataCustom("", null);
        assertEquals(orderServiceSpy.createOrderCustom(orderDataCustom).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected = MessageException.class)
    public void shouldNotCreateCustomOrder_whenNotInformedItems() {
        OrderDataCustom orderDataCustom = new OrderDataCustom("XPTO", null);
        assertEquals(orderServiceSpy.createOrderCustom(orderDataCustom).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected = MessageException.class)
    public void shouldNotCreateCustomOrder_whenEmptyItems() {
        List<OrderDataCustomItem> items = new ArrayList<>();
        OrderDataCustom orderDataCustom = new OrderDataCustom("XPTO", items);
        assertEquals(orderServiceSpy.createOrderCustom(orderDataCustom).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected = MessageException.class)
    public void shouldNotCreateCustomOrder_whenIngredientNotFound() {
        List<OrderDataCustomItem> items = new ArrayList<>();
        items.add(new OrderDataCustomItem("XPTO", 1));
        when(ingredientService.findById("XPTO")).thenReturn(null);
        OrderDataCustom orderDataCustom = new OrderDataCustom("XPTO", items);
        assertEquals(orderServiceSpy.createOrderCustom(orderDataCustom).getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldCreateCustomOrder() {
        List<OrderDataCustomItem> items = asList(new OrderDataCustomItem("XPTO", 1));

        Ingredient ingredient = new Ingredient("Ingredient", 10);
        when(ingredientService.findById("XPTO")).thenReturn(ingredient);

        Order order = new Order("XPTO", null, 0.0, 0);
        when(orderRepository.save(order)).thenReturn(order);

        OrderDataCustom orderDataCustom = new OrderDataCustom("XPTO", items);
        assertEquals(orderServiceSpy.createOrderCustom(orderDataCustom).getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldCalculeDiscount() {
        List<Promotion> promotions = asList(
                new Promotion("",
                        asList(new Ingredient("XPTO","Ingredient", 100)),
                        new ArrayList<>(),
                        1, 10, false));

        when(promotionService.getAllPromotion()).thenReturn(promotions);

        List<OrderDataCustomItem> items = asList(new OrderDataCustomItem("XPTO", 1));
        assertEquals(orderServiceSpy.calculeDiscount(items, 100), 10.0);
    }

    @Test
    public void shouldCalculeDiscount_whenNotHaveAnIngredient() {
        List<Promotion> promotions = asList(
                new Promotion("",
                        asList(new Ingredient("XPTO","Ingredient", 100)),
                        asList(new Ingredient("XPTO2","Ingredient2", 10)),
                        1, 10, true));

        when(promotionService.getAllPromotion()).thenReturn(promotions);

        List<OrderDataCustomItem> items = asList(new OrderDataCustomItem("XPTO", 1));
        assertEquals(orderServiceSpy.calculeDiscount(items, 100), 10.0);
    }
}
