package com.gefa.fit.application.infrastructure;

import com.gefa.fit.application.domain.events.DomainEvent;
import com.gefa.fit.application.domain.events.handlers.DomainEventHandler;
import com.gefa.fit.boundary.outbound.async.fit.FITAssetCreatedEventHandler;
import io.reactivex.subjects.PublishSubject;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Iterator;

@Singleton
public class RXDomainEventsDispatcher {



//	@Inject
//	@RXPublisherSubject
	private PublishSubject<DomainEvent> publishSubject;

//	@Produces
//	@RXPublisherSubject
	public PublishSubject<DomainEvent> createPublishEubject(){
		return PublishSubject.create();
	}

	@Inject
	public RXDomainEventsDispatcher(@Any Instance<DomainEventHandler> handlerList) {
		publishSubject = PublishSubject.create();
		Iterator<DomainEventHandler> handlerIterator = handlerList.iterator();
		while (handlerIterator.hasNext()) {
			DomainEventHandler handler = handlerIterator.next();
			System.out.println("RXDomainEventsDispatcher handler.getClass()="+handler.getClass());
			if (handler instanceof FITAssetCreatedEventHandler) {
				publishSubject.subscribe(handler::handle);
			}
		}
	}

	public void dispatch(DomainEvent domainEvent) {
		publishSubject.onNext(domainEvent);
	}
}
