class Order {

    constructor({ id, client, items, cost, discount}) {
        this._id = id;
        this._client = client;
        this._items = items;
        this._cost = cost;
        this._discount = discount;
    }

    get id() {
        return this._id;
    }

    get client() {
        return this._client;
    }

    get items() {
        return this._items.map(item => {
            if (_.has(item, "snack")) {
                const { snack } = item;
                const { description } = snack;
                if (description.toLowerCase() === "custom") {
                    return snack.ingredients.map(ingredient => ingredient.description).join(", ");
                }
                return description;
            }
        }).join(", ");
    }

    get cost() {
        return this._cost;
    }

    get discount() {
        return this._discount;
    }

}

export default Order;