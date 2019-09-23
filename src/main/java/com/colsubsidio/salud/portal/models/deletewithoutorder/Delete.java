package com.colsubsidio.salud.portal.models.deletewithoutorder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Delete {

	private DeleteWithoutOrder borrarSinOrden;

	public Delete() {
		this.borrarSinOrden = new DeleteWithoutOrder();
	}

}
