const normalize = values => {
    const parameters = values.split("&");
    return parameters.reduce((obj, parameter) => {
        const fields = parameter.split("=");
        obj[fields[0]] = decodeURI(fields[1]);
        return obj;
    }, {});
}

export { normalize };