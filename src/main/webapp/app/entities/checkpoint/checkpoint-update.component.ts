import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICheckpoint, Checkpoint } from 'app/shared/model/checkpoint.model';
import { CheckpointService } from './checkpoint.service';
import { IShipment } from 'app/shared/model/shipment.model';
import { ShipmentService } from 'app/entities/shipment/shipment.service';

@Component({
  selector: 'jhi-checkpoint-update',
  templateUrl: './checkpoint-update.component.html',
})
export class CheckpointUpdateComponent implements OnInit {
  isSaving = false;
  shipments: IShipment[] = [];

  editForm = this.fb.group({
    id: [],
    status: [],
    subStatus: [],
    shipmentId: [],
  });

  constructor(
    protected checkpointService: CheckpointService,
    protected shipmentService: ShipmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkpoint }) => {
      this.updateForm(checkpoint);

      this.shipmentService.query().subscribe((res: HttpResponse<IShipment[]>) => (this.shipments = res.body || []));
    });
  }

  updateForm(checkpoint: ICheckpoint): void {
    this.editForm.patchValue({
      id: checkpoint.id,
      status: checkpoint.status,
      subStatus: checkpoint.subStatus,
      shipmentId: checkpoint.shipmentId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkpoint = this.createFromForm();
    if (checkpoint.id !== undefined) {
      this.subscribeToSaveResponse(this.checkpointService.update(checkpoint));
    } else {
      this.subscribeToSaveResponse(this.checkpointService.create(checkpoint));
    }
  }

  private createFromForm(): ICheckpoint {
    return {
      ...new Checkpoint(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      subStatus: this.editForm.get(['subStatus'])!.value,
      shipmentId: this.editForm.get(['shipmentId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckpoint>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IShipment): any {
    return item.id;
  }
}
