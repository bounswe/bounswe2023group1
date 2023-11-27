export class Node {
    id
    data
    children
    parent

    constructor(id, data, children, parent) {
        this.id = id
        this.data = data
        this.children = children
        this.parent = parent
    }

    getAllParentCategories() {
        if (this.parent.data === "")
            return [this]
        return [this, ...this.parent.getAllParentCategories()]
    }

    findCategoryWithId(id) {
        if (this.id === id) {
            return this
        } else {
            for(const child of this.children){
                let cat = child.findCategoryWithId(id)
                if (cat)
                    return cat
            }
            return null
        }
    }

    isChildCategory(id) {
        if (this.id === id) {
            return true
        } else {
            return !this.children.every(child => !child.isChildCategory(id))
        }
    }

    static createFromArray(json, parent) {
        return json.map(item => {
            const children = []
            const node = new Node(item.id, item.data, children, parent)
            children.push(...Node.createFromArray(item.children, node))
            return node
        })
    }
}

export class RootNode extends Node {
    constructor(json) {
        super("-1", "", [], null);
        this.children.push(...Node.createFromArray(json, this))
    }
}