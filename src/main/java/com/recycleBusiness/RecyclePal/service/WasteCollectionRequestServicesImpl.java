package com.recycleBusiness.RecyclePal.service;

import com.recycleBusiness.RecyclePal.data.models.Customer;
import com.recycleBusiness.RecyclePal.data.models.WasteCollectionRequest;
import com.recycleBusiness.RecyclePal.data.repository.WasteCollectionRequestRepository;
import com.recycleBusiness.RecyclePal.dto.request.CustomerSubmitRequest;
import com.recycleBusiness.RecyclePal.dto.request.WasteCollectionRequestDto;
import com.recycleBusiness.RecyclePal.dto.responce.CustomerSubmitResponse;
import com.recycleBusiness.RecyclePal.dto.responce.WasteCollectionResponse;
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
    public WasteCollectionResponse createRequestDetails(WasteCollectionRequestDto requestDto) throws WasteNotCreated {
        WasteCollectionRequest collectionRequest = modelMapper.map(requestDto, WasteCollectionRequest.class);
         wasteCollectionRequestRepository.save(collectionRequest);
         boolean isSaved = collectionRequest.getRequesterId() != null;
         if (!isSaved)
             throw new WasteNotCreated(WASTE_NOT_CREATED);
        return buildCreateWasteResponse(collectionRequest);
    }


    public CustomerSubmitResponse submitRequest(CustomerSubmitRequest request) throws WasteNotCreated {
        Customer customer = modelMapper.map(request, Customer.class);
        WasteCollectionRequestDto wasteCollection = wasteCollectionBuild(request);
        customer.setAddress(wasteCollection.getAddress());
        wasteCollection.setRequesterId(customer.getId());
        //wasteCollectionServices.createRequestDetails(wasteCollection);
        return CustomerSubmitResponse.builder().message(WASTE_COLLECTION_IS_SUCCESSFULLY_CREATED).build();
    }
    private WasteCollectionRequestDto wasteCollectionBuild(CustomerSubmitRequest request) {
        return WasteCollectionRequestDto.builder()
                .createdTime(request.getCreatedTime())
                .pickedUptime(request.getPickedUptime())
                .quantity(request.getQuantity())
                .build();
    }


    @Override
    public WasteCollectionResponse updateRequestDetails(WasteCollectionRequestDto requestDto) {

        return null;
    }

    private WasteCollectionResponse buildCreateWasteResponse(WasteCollectionRequest collectionRequest){
        return WasteCollectionResponse.builder()
                .message(WASTE_COLLECTION_IS_SUCCESSFULLY_CREATED)
                .build();
    }
}
