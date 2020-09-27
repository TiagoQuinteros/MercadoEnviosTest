import { Status } from 'app/shared/model/enumerations/status.model';
import { SubStatus } from 'app/shared/model/enumerations/sub-status.model';

export interface ICheckpoint {
  id?: number;
  status?: Status;
  subStatus?: SubStatus;
  shipmentId?: number;
}

export class Checkpoint implements ICheckpoint {
  constructor(public id?: number, public status?: Status, public subStatus?: SubStatus, public shipmentId?: number) {}
}
