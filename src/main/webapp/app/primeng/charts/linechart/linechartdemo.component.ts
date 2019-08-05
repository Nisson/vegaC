import { Component, OnInit } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import { Message } from 'primeng/components/common/api';

@Component({
    selector: 'jhi-linechart',
    templateUrl: '../../charts/linechart/linechartdemo.component.html',
    styles: []
})
export class LinechartDemoComponent implements OnInit {
    data: any;
    msgs: Message[];

    constructor() {
        this.data = {
            labels: ['janvier', 'Fevrier', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet','Aout','Septembre','Octobre','Novembre','Decembre'],
            datasets: [
                {
                    label: 'lol',
                    data: [65, 59, 80, 81, 56, 55, 40,50,12,18,20,90],
                    fill: false,
                    borderColor: '#4bc0c0'
                },
                {
                    label: 'Second Dataset',
                    data: [28, 48, 40, 19, 86, 27, 90,100,12,13,15,85],
                    fill: false,
                    borderColor: '#565656'
                }
            ]
        };
    }

    ngOnInit() {
    }

    selectData(event) {
        this.msgs = [];
        this.msgs.push({severity: 'info', summary: 'Data Selected', 'detail': this.data.datasets[event.element._datasetIndex].data[event.element._index]});
    }
}
