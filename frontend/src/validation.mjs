export function isValidTitle(value){
    if(!value) return false;
    return value.trim().length > 0;
}
