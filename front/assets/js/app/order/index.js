import OrderService from "./order.service.js";

OrderService.getAll().then(orders => {
    if (!_.isEmpty(orders)) {
        const $view = $(".js-viewOrders");
        if (!_.isNull($view)) {
            $view.empty();
            orders.forEach(order => {
                $view.append(`
                    <tr>
                        <td>${order.client}</td>
                        <td>${order.items}</td>
                        <td>${order.cost}</td>
                        <td>${order.discount}</td>
                    </tr>
                `);
            });
        }
    }
});