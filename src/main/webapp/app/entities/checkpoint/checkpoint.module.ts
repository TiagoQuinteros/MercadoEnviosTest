import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MercadoEnviosTestSharedModule } from 'app/shared/shared.module';
import { CheckpointComponent } from './checkpoint.component';
import { CheckpointDetailComponent } from './checkpoint-detail.component';
import { CheckpointUpdateComponent } from './checkpoint-update.component';
import { CheckpointDeleteDialogComponent } from './checkpoint-delete-dialog.component';
import { checkpointRoute } from './checkpoint.route';

@NgModule({
  imports: [MercadoEnviosTestSharedModule, RouterModule.forChild(checkpointRoute)],
  declarations: [CheckpointComponent, CheckpointDetailComponent, CheckpointUpdateComponent, CheckpointDeleteDialogComponent],
  entryComponents: [CheckpointDeleteDialogComponent],
})
export class MercadoEnviosTestCheckpointModule {}
