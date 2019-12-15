import store from 'store/store'
import ProductApi from "../api/product";

export class ProductService {
    constructor(props) {
        this.api = new ProductApi(store)
    }

    add(data) {
        return this.api.post(data)
    }
}

export default new ProductService()
