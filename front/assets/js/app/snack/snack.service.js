import Snack from "./snack.js";

class SnackService {

    static getAll() {
        return new Promise((resolve, reject) => {
            $.ajax("http://localhost:8080/api/v1/snacks")
                .done(({ content }) => {
                    if (!_.isEmpty(content)) {
                        resolve(content.map(item => new Snack(item)));
                    }
                })
                .fail(() => console.error("An error was raised on getting Snacks from API"));
        });
    }

}

export default SnackService;