import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VegaCSharedModule } from '../../../shared';
import {FormsModule} from '@angular/forms';
import {ColorPickerModule} from 'primeng/components/colorpicker/colorpicker';
import {GrowlModule} from 'primeng/components/growl/growl';
import {WizardModule} from 'primeng-extensions/components/wizard/wizard.js';

import {
    ColorpickerDemoComponent,
    colorpickerDemoRoute
} from './';

const primeng_STATES = [
    colorpickerDemoRoute
];

@NgModule({
    imports: [
        VegaCSharedModule,
        FormsModule,
        ColorPickerModule,
        GrowlModule,
        WizardModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        ColorpickerDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VegaCColorPickerDemoModule {}
