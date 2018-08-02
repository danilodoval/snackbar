import SnackService from "./snack.service.js";

SnackService.getAll().then(snacks => {
    if (!_.isEmpty(snacks)) {
        const $view = $(".js-viewSnacks");
        if (!_.isNull($view)) {
            $view.empty();
            snacks.forEach(snack => {
                $view.append(`
                    <tr>
                        <td>${snack.description}</td>
                        <td>${snack.ingredients}</td>
                    </tr>
                `);
            });
        }
    }
});