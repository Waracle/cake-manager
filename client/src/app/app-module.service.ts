import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppModuleService {
  visible: boolean;
  constructor() { }

  isVisible(): boolean{
  console.log('app-component service isVisible: '+ this.visible);
      return this.visible;
    }

    setVisible(visible: boolean){
    console.log('app-component service setVisible: '+ visible);
      this.visible = visible;
    }
}
