class Snack {

    constructor({id, description, ingredients}) {
        this._id = id;
        this._description = description;
        this._ingredients = ingredients;
    }

    get id() {
        return this._id;
    }

    get description() {
        return this._description;
    }

    get ingredients() {
        return this._ingredients.map(ingredient => ingredient.description).join(", ");
    }

}

export default Snack;