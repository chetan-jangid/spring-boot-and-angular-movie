export interface MultiplexResponse {
  multiplexes?: Multiplex[];
}

export interface Multiplex {
  id?: string;
  multiplexName: string;
  address?: string;
  description?: string;
  screens?: Screen[];
}

export interface Screen {
  id?: string;
  name: string;
  multiplexId?: string;
}
