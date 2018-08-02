import Order from "./order.js";
import { normalize } from "../../utils/form.js";

const ORDER_ENDPOINT = "http://localhost:8080/api/v1/orders";

class OrderService {

    static create(values, $customIngredients) {
        return new Promise((resolve, reject) => {
            let obj;
            let URL = ORDER_ENDPOINT;
            const nativeCustomIngredients = $customIngredients[0];
            const data = normalize(values);
            if (_.isUndefined(nativeCustomIngredients)) {
                obj = Object.assign({ snacks : [{
                    id : data.snack,
                    quantity : 1
                }]}, data);
            } else {
                const fields = [];
                $.each($customIngredients.find("td"), (index, td) => fields.push({ ingredientId : $(td).attr("data-id"), quantity : 1 }));
                obj = Object.assign({ items : fields }, data);
                URL += "/custom";
            }
            $.ajax({
                contentType : "application/json",
                data : JSON.stringify(obj),
                method : "POST",
                url: URL
            }).done(resolve).fail(reject);
        });
    }

    static getAll() {
        return new Promise((resolve, reject) => {
            $.ajax(ORDER_ENDPOINT)
                .done(({ content }) => {
                    if (!_.isEmpty(content)) {
                        resolve(content.map(item => new Order(item)));
                    }
                })
                .fail(() => console.error("An error was raised on getting Orders from API"));
        });
    }

}

export default OrderService;