import { Injectable, EventEmitter } from '@angular/core';

/**
 * Class responsible for controlling the 'Loader' component.
 *
 * @author Manoel Veríssimo
 */
@Injectable()
export class LoaderService {

    public onStart: EventEmitter<void>;

    public onStop: EventEmitter<void>;

    /**
     * Constructor classe.
     */
    constructor() {
        this.onStart = new EventEmitter<void>();
        this.onStop = new EventEmitter<void>();
    }

}