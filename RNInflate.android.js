import { Model, Inflater } from './bridge'

const model = Model(null)
class RNInflate extends Inflater {
  constructor(irModel) {
		super(irModel)
	}
}

export default new RNInflate(model)
