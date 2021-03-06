package com.gefa.fit.boundary.inbound.rest.activities;

import com.gefa.fit.application.AssetService;
import com.gefa.fit.application.domain.Asset;
import com.gefa.fit.application.exceptions.AssetRemovalException;
import com.gefa.fit.boundary.inbound.rest.representations.AssetRepresentation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RemoveAssetActivity {

	@Inject
	private AssetService assetService;

	public AssetRepresentation delete(Long assetId) throws AssetRemovalException {

		Asset asset = assetService.removeAsset(assetId);
		

		return new AssetRepresentation(asset);
	}

}
