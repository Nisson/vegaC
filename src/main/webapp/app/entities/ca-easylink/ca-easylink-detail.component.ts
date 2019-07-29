import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICAEasylink } from 'app/shared/model/ca-easylink.model';

@Component({
  selector: 'jhi-ca-easylink-detail',
  templateUrl: './ca-easylink-detail.component.html'
})
export class CAEasylinkDetailComponent implements OnInit {
  cAEasylink: ICAEasylink;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cAEasylink }) => {
      this.cAEasylink = cAEasylink;
    });
  }

  previousState() {
    window.history.back();
  }
}
