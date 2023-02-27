const snakeToCamel = (s) => {
    return s.toLowerCase().replace(/[-_][a-z]/g, (group) => group.slice(-1).toUpperCase());
};