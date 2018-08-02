class Ingredient {

    constructor({id, description, cost}) {
        this._id = id;
        this._description = description;
        this._cost = cost;
    }

    get id() {
        return this._id;
    }
    
    get description() {
        return this._description;
    }
    
    get cost() {
        return this._cost;
    }

}

export default Ingredient;