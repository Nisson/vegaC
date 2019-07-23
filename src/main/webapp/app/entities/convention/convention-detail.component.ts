import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConvention } from 'app/shared/model/convention.model';

@Component({
  selector: 'jhi-convention-detail',
  templateUrl: './convention-detail.component.html'
})
export class ConventionDetailComponent implements OnInit {
  convention: IConvention;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ convention }) => {
      this.convention = convention;
    });
  }

  previousState() {
    window.history.back();
  }
}
