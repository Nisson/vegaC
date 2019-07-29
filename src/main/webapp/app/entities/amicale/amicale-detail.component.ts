import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAmicale } from 'app/shared/model/amicale.model';

@Component({
  selector: 'jhi-amicale-detail',
  templateUrl: './amicale-detail.component.html'
})
export class AmicaleDetailComponent implements OnInit {
  amicale: IAmicale;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amicale }) => {
      this.amicale = amicale;
    });
  }

  previousState() {
    window.history.back();
  }
}
