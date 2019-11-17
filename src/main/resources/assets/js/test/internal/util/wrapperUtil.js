import {Wrapper} from '@vue/test-utils'

export const filter = {
    classes: (elem, selector, classes) => elem.contains(selector) && elem.find(selector).classes(classes)
}

export const wrap = {
    do: (elems) => new ConditionBuilder(elems)
}

class ConditionBuilder {
    constructor(elems) {
        this.elems = elems

        this.classes = []
        this.selectors = []
        this.strings = []
        this.attributes = []
    }

    class(clazz) {
        this.classes.push(clazz)
        return this
    }

    selector(selector) {
        this.selectors.push(selector)
        return this
    }

    string(string) {
        this.strings.push(string)
        return this
    }

    attr(key, value, strict) {
        this.attributes.push({
            attr: key,
            value: value,
            strict: !!strict
        })
        return this
    }

    filter(minSelectors) {
        let that = this

        return this.elems instanceof Wrapper ? [this.filterElem(this.elems)] : this.elems.filter((elem) => {
            return that.filterElem.apply(that, [elem]) >= minSelectors
        }).wrappers
    }

    filterElem(elem) {
        let that = this
        return this.selectors.length ? this.selectors.filter(selector => {
            let elems = elem.findAll(selector)

            return elems.filter((elem) => {
                return that.filterSelector.apply(that, [elem])
            }).length
        }).length : this.filterSelector(elem)
    }

    filterSelector(elem) {
        if (this.isClasses.apply(this, [elem])) {
            return false
        }

        if (this.isAttributes.apply(this, [elem])) {
            return false
        }

        if (this.isStrings.apply(this, [elem])) {
            return false
        }

        return true
    }

    isClasses(elem) {
        return this.classes.attributes && !this.classes.filter((clazz) => elem.classes().contains(clazz).length)
    }

    isStrings(elem) {
        return this.strings.length && !this.strings.filter((string) => elem.text().includes(string).length)
    }

    isAttributes(elem) {
        return this.attributes.length
            && !this.attributes.filter((attr) => attr.strict
                ? elem.attributes(attr.attr) === attr.value
                : elem.attributes(attr.attr).includes(attr.value))
    }

    noOne() {
        return this.filter(1).length === 0
    }

    one() {
        return this.filter(1).length === 1
    }

    any() {
        return this.filter(1).length > 0
    }

    all() {
        return this.filter(1).length === this.elems.length
    }
}
