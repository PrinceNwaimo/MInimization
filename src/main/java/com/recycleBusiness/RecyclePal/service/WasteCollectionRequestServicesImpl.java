package com.recycleBusiness.RecyclePal.service;

import com.recycleBusiness.RecyclePal.data.models.Ecopal;
import com.recycleBusiness.RecyclePal.data.models.PlasticPickUp;
import com.recycleBusiness.RecyclePal.data.repository.WasteCollectionRequestRepository;
import com.recycleBusiness.RecyclePal.dto.request.EcopalSubmitRequest;
import com.recycleBusiness.RecyclePal.dto.request.PlasticPickUpRequest;
import com.recycleBusiness.RecyclePal.dto.response.EcopalSubmitResponse;
import com.recycleBusiness.RecyclePal.dto.response.PlasticPickUpResponse;
import com.recycleBusiness.RecyclePal.exception.WasteNotCreated;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.recycleBusiness.RecyclePal.utils.ErrorMessageClass.WASTE_NOT_CREATED;
import static com.recycleBusiness.RecyclePal.utils.ResponseMessage.WASTE_COLLECTION_IS_SUCCESSFULLY_CREATED;

@RequiredArgsConstructor
@Service
public class WasteCollectionRequestServicesImpl implements WasteCollectionRequestServices {

    private final WasteCollectionRequestRepository wasteCollectionRequestRepository;
    private final ModelMapper modelMapper;

    @Override
    public PlasticPickUpResponse createRequestDetails(PlasticPickUpRequest requestDto) throws WasteNotCreated {
        PlasticPickUp collectionRequest = modelMapper.map(requestDto, PlasticPickUp.class);
         wasteCollectionRequestRepository.save(collectionRequest);
         boolean isSaved = collectionRequest.getPickerId() != null;
         if (!isSaved)
             throw new WasteNotCreated(WASTE_NOT_CREATED);
        return buildCreateWasteResponse(collectionRequest);
    }


    public EcopalSubmitResponse submitRequest(EcopalSubmitRequest request) throws WasteNotCreated {
        Ecopal customer = modelMapper.map(request, Ecopal.class);
        PlasticPickUpRequest wasteCollection = wasteCollectionBuild(request);
        customer.setAddress(wasteCollection.getAddress());
//        wasteCollection.setRequesterId(customer.getId());
        //wasteCollectionServices.createRequestDetails(wasteCollection);
        return EcopalSubmitResponse.builder().message(WASTE_COLLECTION_IS_SUCCESSFULLY_CREATED).build();
    }
    private PlasticPickUpRequest wasteCollectionBuild(EcopalSubmitRequest request) {
        return PlasticPickUpRequest.builder()
                .createdTime(request.getCreatedTime())
                .pickedUptime(request.getPickedUptime())
                .quantity(request.getQuantity())
                .build();
    }


    @Override
    public PlasticPickUpResponse updateRequestDetails(PlasticPickUpRequest requestDto) {

        return null;
    }

    private PlasticPickUpResponse buildCreateWasteResponse(PlasticPickUp collectionRequest){
        return PlasticPickUpResponse.builder()
                .message(WASTE_COLLECTION_IS_SUCCESSFULLY_CREATED)
                .build();
    }
}
