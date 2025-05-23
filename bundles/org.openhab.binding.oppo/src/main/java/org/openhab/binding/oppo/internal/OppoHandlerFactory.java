/*
 * Copyright (c) 2010-2025 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.oppo.internal;

import static org.openhab.binding.oppo.internal.OppoBindingConstants.*;

import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.oppo.internal.handler.OppoHandler;
import org.openhab.core.i18n.LocaleProvider;
import org.openhab.core.i18n.TranslationProvider;
import org.openhab.core.io.transport.serial.SerialPortManager;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link OppoHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Michael Lobstein - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.oppo", service = ThingHandlerFactory.class)
public class OppoHandlerFactory extends BaseThingHandlerFactory {
    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Set.of(THING_TYPE_PLAYER);

    private final SerialPortManager serialPortManager;
    private final TranslationProvider translationProvider;
    private final LocaleProvider localeProvider;

    private final OppoStateDescriptionOptionProvider stateDescriptionProvider;

    @Activate
    public OppoHandlerFactory(final @Reference OppoStateDescriptionOptionProvider provider,
            final @Reference SerialPortManager serialPortManager, @Reference TranslationProvider translationProvider,
            @Reference LocaleProvider localeProvider) {
        this.stateDescriptionProvider = provider;
        this.serialPortManager = serialPortManager;
        this.translationProvider = translationProvider;
        this.localeProvider = localeProvider;
    }

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID)) {
            return new OppoHandler(thing, stateDescriptionProvider, serialPortManager, translationProvider,
                    localeProvider);
        }
        return null;
    }
}
