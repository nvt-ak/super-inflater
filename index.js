import { Model, Inflater } from './bridge'

const model = Model(null)
class SuperInflater extends Inflater {
  constructor(irModel) {
    super(irModel)
  }
}

export default new SuperInflater(model)
