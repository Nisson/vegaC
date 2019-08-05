
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VegaCButtonDemoModule } from './buttons/button/buttondemo.module';
import { VegaCSplitbuttonDemoModule } from './buttons/splitbutton/splitbuttondemo.module';

import { VegaCDialogDemoModule } from './overlay/dialog/dialogdemo.module';
import { VegaCConfirmDialogDemoModule } from './overlay/confirmdialog/confirmdialogdemo.module';
import { VegaCLightboxDemoModule } from './overlay/lightbox/lightboxdemo.module';
import { VegaCTooltipDemoModule } from './overlay/tooltip/tooltipdemo.module';
import { VegaCOverlayPanelDemoModule } from './overlay/overlaypanel/overlaypaneldemo.module';
import { VegaCSideBarDemoModule } from './overlay/sidebar/sidebardemo.module';

import { VegaCKeyFilterDemoModule } from './inputs/keyfilter/keyfilterdemo.module';
import { VegaCInputTextDemoModule } from './inputs/inputtext/inputtextdemo.module';
import { VegaCInputTextAreaDemoModule } from './inputs/inputtextarea/inputtextareademo.module';
import { VegaCInputGroupDemoModule } from './inputs/inputgroup/inputgroupdemo.module';
import { VegaCCalendarDemoModule } from './inputs/calendar/calendardemo.module';
import { VegaCCheckboxDemoModule } from './inputs/checkbox/checkboxdemo.module';
import { VegaCChipsDemoModule } from './inputs/chips/chipsdemo.module';
import { VegaCColorPickerDemoModule } from './inputs/colorpicker/colorpickerdemo.module';
import { VegaCInputMaskDemoModule } from './inputs/inputmask/inputmaskdemo.module';
import { VegaCInputSwitchDemoModule } from './inputs/inputswitch/inputswitchdemo.module';
import { VegaCPasswordIndicatorDemoModule } from './inputs/passwordindicator/passwordindicatordemo.module';
import { VegaCAutoCompleteDemoModule } from './inputs/autocomplete/autocompletedemo.module';
import { VegaCSliderDemoModule } from './inputs/slider/sliderdemo.module';
import { VegaCSpinnerDemoModule } from './inputs/spinner/spinnerdemo.module';
import { VegaCRatingDemoModule } from './inputs/rating/ratingdemo.module';
import { VegaCSelectDemoModule } from './inputs/select/selectdemo.module';
import { VegaCSelectButtonDemoModule } from './inputs/selectbutton/selectbuttondemo.module';
import { VegaCListboxDemoModule } from './inputs/listbox/listboxdemo.module';
import { VegaCRadioButtonDemoModule } from './inputs/radiobutton/radiobuttondemo.module';
import { VegaCToggleButtonDemoModule } from './inputs/togglebutton/togglebuttondemo.module';
import { VegaCEditorDemoModule } from './inputs/editor/editordemo.module';

import { VegaCMessagesDemoModule } from './messages/messages/messagesdemo.module';
import { VegaCToastDemoModule } from './messages/toast/toastdemo.module';
import { VegaCGalleriaDemoModule } from './multimedia/galleria/galleriademo.module';

import { VegaCFileUploadDemoModule } from './fileupload/fileupload/fileuploaddemo.module';

import { VegaCAccordionDemoModule } from './panel/accordion/accordiondemo.module';
import { VegaCPanelDemoModule } from './panel/panel/paneldemo.module';
import { VegaCTabViewDemoModule } from './panel/tabview/tabviewdemo.module';
import { VegaCFieldsetDemoModule } from './panel/fieldset/fieldsetdemo.module';
import { VegaCToolbarDemoModule } from './panel/toolbar/toolbardemo.module';
import { VegaCScrollPanelDemoModule } from './panel/scrollpanel/scrollpaneldemo.module';
import { VegaCCardDemoModule } from './panel/card/carddemo.module';
import { VegaCFlexGridDemoModule } from './panel/flexgrid/flexgriddemo.module';

import { VegaCTableDemoModule } from './data/table/tabledemo.module';
import { VegaCVirtualScrollerDemoModule } from './data/virtualscroller/virtualscrollerdemo.module';
import { VegaCPickListDemoModule } from './data/picklist/picklistdemo.module';
import { VegaCOrderListDemoModule } from './data/orderlist/orderlistdemo.module';
import { VegaCFullCalendarDemoModule } from './data/fullcalendar/fullcalendardemo.module';
import { VegaCTreeDemoModule } from './data/tree/treedemo.module';
import { VegaCTreeTableDemoModule } from './data/treetable/treetabledemo.module';
import { VegaCPaginatorDemoModule } from './data/paginator/paginatordemo.module';
import { VegaCGmapDemoModule } from './data/gmap/gmapdemo.module';
import { VegaCOrgChartDemoModule } from './data/orgchart/orgchartdemo.module';
import { VegaCCarouselDemoModule } from './data/carousel/carouseldemo.module';
import { VegaCDataViewDemoModule } from './data/dataview/dataviewdemo.module';

import { VegaCBarchartDemoModule } from './charts/barchart/barchartdemo.module';
import { VegaCDoughnutchartDemoModule } from './charts/doughnutchart/doughnutchartdemo.module';
import { VegaCLinechartDemoModule } from './charts/linechart/linechartdemo.module';
import { VegaCPiechartDemoModule } from './charts/piechart/piechartdemo.module';
import { VegaCPolarareachartDemoModule } from './charts/polarareachart/polarareachartdemo.module';
import { VegaCRadarchartDemoModule } from './charts/radarchart/radarchartdemo.module';

import { VegaCDragDropDemoModule } from './dragdrop/dragdrop/dragdropdemo.module';

import { VegaCMenuDemoModule } from './menu/menu/menudemo.module';
import { VegaCContextMenuDemoModule } from './menu/contextmenu/contextmenudemo.module';
import { VegaCPanelMenuDemoModule } from './menu/panelmenu/panelmenudemo.module';
import { VegaCStepsDemoModule } from './menu/steps/stepsdemo.module';
import { VegaCTieredMenuDemoModule } from './menu/tieredmenu/tieredmenudemo.module';
import { VegaCBreadcrumbDemoModule } from './menu/breadcrumb/breadcrumbdemo.module';
import { VegaCMegaMenuDemoModule } from './menu/megamenu/megamenudemo.module';
import { VegaCMenuBarDemoModule } from './menu/menubar/menubardemo.module';
import { VegaCSlideMenuDemoModule } from './menu/slidemenu/slidemenudemo.module';
import { VegaCTabMenuDemoModule } from './menu/tabmenu/tabmenudemo.module';

import { VegaCBlockUIDemoModule } from './misc/blockui/blockuidemo.module';
import { VegaCCaptchaDemoModule } from './misc/captcha/captchademo.module';
import { VegaCDeferDemoModule } from './misc/defer/deferdemo.module';
import { VegaCInplaceDemoModule } from './misc/inplace/inplacedemo.module';
import { VegaCProgressBarDemoModule } from './misc/progressbar/progressbardemo.module';
import { VegaCRTLDemoModule } from './misc/rtl/rtldemo.module';
import { VegaCTerminalDemoModule } from './misc/terminal/terminaldemo.module';
import { VegaCValidationDemoModule } from './misc/validation/validationdemo.module';
import { VegaCProgressSpinnerDemoModule } from './misc/progressspinner/progressspinnerdemo.module';

@NgModule({
    imports: [

        VegaCMenuDemoModule,
        VegaCContextMenuDemoModule,
        VegaCPanelMenuDemoModule,
        VegaCStepsDemoModule,
        VegaCTieredMenuDemoModule,
        VegaCBreadcrumbDemoModule,
        VegaCMegaMenuDemoModule,
        VegaCMenuBarDemoModule,
        VegaCSlideMenuDemoModule,
        VegaCTabMenuDemoModule,

        VegaCBlockUIDemoModule,
        VegaCCaptchaDemoModule,
        VegaCDeferDemoModule,
        VegaCInplaceDemoModule,
        VegaCProgressBarDemoModule,
        VegaCInputMaskDemoModule,
        VegaCRTLDemoModule,
        VegaCTerminalDemoModule,
        VegaCValidationDemoModule,

        VegaCButtonDemoModule,
        VegaCSplitbuttonDemoModule,

        VegaCInputTextDemoModule,
        VegaCInputTextAreaDemoModule,
        VegaCInputGroupDemoModule,
        VegaCCalendarDemoModule,
        VegaCChipsDemoModule,
        VegaCInputMaskDemoModule,
        VegaCInputSwitchDemoModule,
        VegaCPasswordIndicatorDemoModule,
        VegaCAutoCompleteDemoModule,
        VegaCSliderDemoModule,
        VegaCSpinnerDemoModule,
        VegaCRatingDemoModule,
        VegaCSelectDemoModule,
        VegaCSelectButtonDemoModule,
        VegaCListboxDemoModule,
        VegaCRadioButtonDemoModule,
        VegaCToggleButtonDemoModule,
        VegaCEditorDemoModule,
        VegaCColorPickerDemoModule,
        VegaCCheckboxDemoModule,
        VegaCKeyFilterDemoModule,

        VegaCMessagesDemoModule,
        VegaCToastDemoModule,
        VegaCGalleriaDemoModule,

        VegaCFileUploadDemoModule,

        VegaCAccordionDemoModule,
        VegaCPanelDemoModule,
        VegaCTabViewDemoModule,
        VegaCFieldsetDemoModule,
        VegaCToolbarDemoModule,
        VegaCScrollPanelDemoModule,
        VegaCCardDemoModule,
        VegaCFlexGridDemoModule,

        VegaCBarchartDemoModule,
        VegaCDoughnutchartDemoModule,
        VegaCLinechartDemoModule,
        VegaCPiechartDemoModule,
        VegaCPolarareachartDemoModule,
        VegaCRadarchartDemoModule,

        VegaCDragDropDemoModule,

        VegaCDialogDemoModule,
        VegaCConfirmDialogDemoModule,
        VegaCLightboxDemoModule,
        VegaCTooltipDemoModule,
        VegaCOverlayPanelDemoModule,
        VegaCSideBarDemoModule,

        VegaCTableDemoModule,
        VegaCDataViewDemoModule,
        VegaCVirtualScrollerDemoModule,
        VegaCFullCalendarDemoModule,
        VegaCOrderListDemoModule,
        VegaCPickListDemoModule,
        VegaCTreeDemoModule,
        VegaCTreeTableDemoModule,
        VegaCPaginatorDemoModule,
        VegaCOrgChartDemoModule,
        VegaCGmapDemoModule,
        VegaCCarouselDemoModule,
        VegaCProgressSpinnerDemoModule

    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VegaCprimengModule {}
