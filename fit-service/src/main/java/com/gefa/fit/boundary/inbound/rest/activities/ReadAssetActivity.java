package com.gefa.fit.boundary.inbound.rest.activities;

import com.gefa.fit.application.AssetService;
import com.gefa.fit.application.domain.Asset;
import com.gefa.fit.application.domain.AssetCreatedState;
import com.gefa.fit.application.exceptions.NoSuchAssetException;
import com.gefa.fit.boundary.inbound.rest.representations.AssetRepresentation;
import com.gefa.fit.boundary.inbound.rest.representations.Link;
import com.gefa.fit.boundary.inbound.rest.representations.converters.AssetConverter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
public class ReadAssetActivity {

	@Inject
	private AssetService assetService;

	@Inject
	private AssetConverter assetConverter;

	public ReadAssetActivity() {
		// TODO Auto-generated constructor stub
	}

	public AssetRepresentation retrieveById(Long assetId, UriInfo uriInfo) throws NoSuchAssetException {
		Asset asset = assetService.getAsset(assetId);

		if (null == asset) {
			throw new NoSuchAssetException("Asset with id " + assetId + " not found");
		}

		String assetURI = uriInfo.getRequestUri().toString();

		Link assetSelflink = new Link("self", assetURI, MediaType.APPLICATION_XML);
		AssetRepresentation assetRepresentation = new AssetRepresentation(asset, assetSelflink);

		if (asset.getAssetState().equals(new AssetCreatedState(asset))) {
			String approveURI = uriInfo.getBaseUri() + "approve/" + assetId;
			Link assetApprovelink = new Link("approve", approveURI, MediaType.APPLICATION_XML);
			Link assetUpdatelink = new Link("update", assetURI, MediaType.APPLICATION_XML);
			Link assetDeletelink = new Link("remove", assetURI, MediaType.APPLICATION_XML);
			assetRepresentation = new AssetRepresentation(asset, assetSelflink, assetUpdatelink, assetDeletelink,
					assetApprovelink);
		}

		return assetRepresentation;

	}

}
