import Ingredient from "./ingredient.js";

class IngredientService {

    static getAll() {
        return new Promise((resolve, reject) => {
            $.ajax("http://localhost:8080/api/v1/ingredients")
                .done(({ content }) => {
                    if (!_.isEmpty(content)) {
                        resolve(content.map(item => new Ingredient(item)));
                    }
                })
                .fail(() => console.error("An error was raised on getting Ingredients from API"));
        });
    }

}

export default IngredientService;