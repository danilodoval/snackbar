import IngredientService from "../ingredient/ingredient.service.js";
import SnackService from "../snack/snack.service.js";
import OrderService from "../order/order.service.js";

$(".js-createOrder").click(event => {
    event.preventDefault();
    const $form = $(".js-orderForm");
    const $ingredientsTableBody = $(".js-viewIngredients[data-has-fields='true']");
    OrderService
        .create($form.serialize(), $ingredientsTableBody)
        .then(() => {
            setTimeout(() => $(".js-orderCreated").addClass("d-none"), 3000);
            $(".js-orderCreated").removeClass("d-none");
            $form[0].reset();
            $ingredientsTableBody.append("<tr><td>No ingredients added</td></tr>");
            $(".js-ingredientsSection").addClass("d-none");
        })
        .catch(() => $(".js-orderError").removeClass("d-none"));
});

SnackService.getAll().then(snacks => {
    if (!_.isEmpty(snacks)) {
        const $select = $(".js-selectSnacks");
        if (!_.isNull($select)) {
            $select.change(handleSelectSnack);
            $select.append(new Option("Select a snack for your order"));
            snacks.forEach(snack => {
                $select.append(new Option(snack.description, snack.id));
            });
            $select.append(new Option("Custom", "custom"));
        }
    }
});

function handleIngredientAdd($selectIngredients) {
    const $ingredientsTableBody = $(".js-viewIngredients");
    if (!_.isNull($selectIngredients)) {
        const nativeSelect = $selectIngredients[0];
        const ingredientId = $selectIngredients.val();
        if (!_.isNull(ingredientId) && !_.isUndefined(ingredientId)) {
            if (ingredientId === "Select a ingredient for your order") {
                alert("Please select a ingredient to add it on your order");
            } else {
                const ingredientDescription = $(nativeSelect.options[nativeSelect.selectedIndex]).text();
                if (!_.isNull($ingredientsTableBody)) {
                    if ($ingredientsTableBody.find("td").text() === "No ingredients added") {
                        $ingredientsTableBody.attr("data-has-fields", "true");
                        $ingredientsTableBody.empty();
                    }
                    if (_.isUndefined($ingredientsTableBody.find(`[data-id="${ingredientId}"]`)[0])) {
                        $ingredientsTableBody.append(`
                            <tr>
                                <td data-id="${ingredientId}">
                                    ${ ingredientDescription }
                                </td>
                            </tr>
                        `);
                    }
                }
            }
        }
        $selectIngredients.val(null);
        $selectIngredients[0].selectedIndex = 0;
    }
}

function handleSelectSnack(event) {
    const snackSelected = event.target.value;
    const $selectIngredients = $(".js-selectIngredients");
    if (_.isEqual(snackSelected, "custom")) {
        IngredientService.getAll()
            .then(ingredients => {
                $(".js-addIngredient").click(() => handleIngredientAdd($selectIngredients));
                $selectIngredients.append(new Option("Select a ingredient for your order"));
                ingredients.forEach(ingredient => $selectIngredients.append(new Option(ingredient.description, ingredient.id)));
                $selectIngredients.closest(".js-ingredientsSection").removeClass("d-none");
            });
    } else {
        if (!$selectIngredients.hasClass("d-none")) {
            $selectIngredients.closest(".js-ingredientsSection").addClass("d-none");
        }
    }
}