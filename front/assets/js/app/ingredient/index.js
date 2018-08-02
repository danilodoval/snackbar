import IngredientService from "./ingredient.service.js";

IngredientService.getAll().then(ingredients => {
    if (!_.isEmpty(ingredients)) {
        const $view = $(".js-viewIngredients");
        if (!_.isNull($view)) {
            $view.empty();
            ingredients.forEach(ingredient => {
                $view.append(`
                    <tr>
                        <td>${ingredient.description}</td>
                        <td>${ingredient.cost}</td>
                    </tr>
                `);
            });
        }
    }
});