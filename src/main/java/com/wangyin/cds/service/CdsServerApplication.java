package com.wangyin.cds.service;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.wangyin.cds.service.resource.DbGroupResource;
import com.wangyin.cds.service.resource.DbInfoResource;
import com.wangyin.cds.service.resource.SplittingKeyResource;

public class CdsServerApplication extends ResourceConfig {
    public CdsServerApplication() {
        super(SplittingKeyResource.class, DbInfoResource.class, DbGroupResource.class,
                JacksonJsonProvider.class, JacksonFeature.class);
    }
}
