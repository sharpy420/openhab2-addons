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
package org.openhab.io.homekit.internal.accessories;

import static org.openhab.io.homekit.internal.HomekitCharacteristicType.CARBON_MONOXIDE_DETECTED_STATE;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.openhab.io.homekit.internal.HomekitAccessoryUpdater;
import org.openhab.io.homekit.internal.HomekitException;
import org.openhab.io.homekit.internal.HomekitSettings;
import org.openhab.io.homekit.internal.HomekitTaggedItem;

import io.github.hapjava.accessories.CarbonMonoxideSensorAccessory;
import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.carbonmonoxidesensor.CarbonMonoxideDetectedEnum;
import io.github.hapjava.services.impl.CarbonMonoxideSensorService;

/**
 *
 * @author Cody Cutrer - Initial contribution
 */
public class HomekitCarbonMonoxideSensorImpl extends AbstractHomekitAccessoryImpl
        implements CarbonMonoxideSensorAccessory {
    private final Map<CarbonMonoxideDetectedEnum, Object> mapping;

    public HomekitCarbonMonoxideSensorImpl(HomekitTaggedItem taggedItem,
            List<HomekitTaggedItem> mandatoryCharacteristics, List<Characteristic> mandatoryRawCharacteristics,
            HomekitAccessoryUpdater updater, HomekitSettings settings) throws IncompleteAccessoryException {
        super(taggedItem, mandatoryCharacteristics, mandatoryRawCharacteristics, updater, settings);
        mapping = createMapping(CARBON_MONOXIDE_DETECTED_STATE, CarbonMonoxideDetectedEnum.class);
    }

    @Override
    public void init() throws HomekitException {
        super.init();
        addService(new CarbonMonoxideSensorService(this));
    }

    @Override
    public CompletableFuture<CarbonMonoxideDetectedEnum> getCarbonMonoxideDetectedState() {
        return CompletableFuture.completedFuture(
                getKeyFromMapping(CARBON_MONOXIDE_DETECTED_STATE, mapping, CarbonMonoxideDetectedEnum.NORMAL));
    }

    @Override
    public void subscribeCarbonMonoxideDetectedState(HomekitCharacteristicChangeCallback callback) {
        subscribe(CARBON_MONOXIDE_DETECTED_STATE, callback);
    }

    @Override
    public void unsubscribeCarbonMonoxideDetectedState() {
        unsubscribe(CARBON_MONOXIDE_DETECTED_STATE);
    }
}
