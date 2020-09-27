import { ICheckpoint } from 'app/shared/model/checkpoint.model';

export interface IShipment {
  id?: number;
  checkpoints?: ICheckpoint[];
}

export class Shipment implements IShipment {
  constructor(public id?: number, public checkpoints?: ICheckpoint[]) {}
}
