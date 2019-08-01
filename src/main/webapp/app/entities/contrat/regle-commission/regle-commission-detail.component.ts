import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegleCommission } from 'app/shared/model/regle-commission.model';

@Component({
  selector: 'jhi-regle-commission-detail',
  templateUrl: './regle-commission-detail.component.html'
})
export class RegleCommissionDetailComponent implements OnInit {
  regleCommission: IRegleCommission;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ regleCommission }) => {
      this.regleCommission = regleCommission;
    });
  }

  previousState() {
    window.history.back();
  }
}
