class Inflater {
	constructor(irModel) {
		this.irModel = irModel
	}

	fix(key, value) {
		return this.irModel.fix(key, value)
	}

	// Config for headers
	set(key, value) {
		return this.irModel.set(key, value)
	}

	// Setup for body
	send(key, value) {
		return this.irModel.send(key, value)
	}

	query(key, value) {
		return this.irModel.query(key, value)
	}

	get(url) {
		return this.irModel.get(url)
	}

	post(url) {
		return this.irModel.post(url)
	}

	put(url) {
		return this.irModel.put(url)
	}

	delete(url) {
		return this.irModel.delete(url)
	}

	getInflate(url) {
		return this.irModel.getInflate(url)
	}

	submitInflate(url) {
		return this.irModel.submitInflate(url)
	}

	putInflate(url) {
		return this.irModel.putInflate(url)
	}

	deleteInflate(url) {
		return this.irModel.deleteInflate(url)
	}

	timeout(value) {
		return this.irModel.timeout(value)
	}

	platform(os) {
		return this.irModel.platform(os)
	}
}

export default Inflater