import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { Pair, StaticDataService } from 'src/app/services/static-data.service';
import { AppUtilService } from 'src/app/services/app-util.service';
import { ApiService } from 'src/app/services/api.service';
import { Multiplex } from 'src/app/models/multiplex';

@Component({
  selector: 'app-multiplex',
  templateUrl: './multiplex.component.html',
  styleUrls: ['./multiplex.component.scss']
})
export class MultiplexComponent implements OnInit {

  form: FormGroup;
  actions: Pair[];
  multiplexes: Multiplex[];
  
  constructor(
    private fb: FormBuilder,
    public appUtilService: AppUtilService,
    public apiService: ApiService,
    public staticDataService: StaticDataService) { }

  ngOnInit(): void {
    this.initializeForm("create");
    this.actions = [
      { label: "Create a new multiplex record", value: "create" },
      { label: "Modify existing multiplex details", value: "update" }
    ];
    this.findExistingMultiplex();
  }

  initializeForm(action: string): void {
    this.form = this.fb.group({
      id: [null],
      action: [action, Validators.required],
      multiplexName: [null, [Validators.required, Validators.maxLength(50)]],
      address: [null, [Validators.required, Validators.maxLength(150)]],
      description: [null, Validators.maxLength(500)],
      screens: this.fb.array([])
    });
    (this.form.get("screens") as FormArray).push(this.screenRow());
  }

  screenRow(): FormGroup {
    let length: number = (this.form.get("screens") as FormArray).length;
    if (!length) {
      length = 0;
    }
    return this.fb.group({
      id: [null],
      name: [length + 1, [Validators.required, Validators.maxLength(50)]]
    });
  }

  screenRowWithValues(id: string, name: string): FormGroup {
    return this.fb.group({
      id: [id],
      name: [name, [Validators.required, Validators.maxLength(50)]]
    });
  }

  addScreen(): void {
    let screenFormArray: FormArray = this.form.get("screens") as FormArray;
    if (screenFormArray.length >= 20) {
      this.appUtilService.dialog({ title: "Maximum screens allowed are 20.", type: "info" });
    } else {
      screenFormArray.push(this.screenRow());
    }
  }

  removeScreen(index: number): void {
    let length: number = (this.form.get("screens") as FormArray).length;
    if (length > 1) {
      (this.form.get("screens") as FormArray).removeAt(index);
    }
  }

  getAction(): string {
    return this.form.get("action").value;
  }

  saveOrUpdate(value: string): void {
    let url: string;
    if (value === "save") {
      url = "/multiplex/manage/save";
      this.form.get("id").clearValidators();
      this.form.get("id").updateValueAndValidity();
    } else {
      url = "/multiplex/manage/update";
      this.form.get("id").setValidators([Validators.required]);
      this.form.get("id").updateValueAndValidity();
    }
    this.appUtilService.checkFormValidations(this.form);
    if (this.form.valid) {
      this.apiService.post({ url: url, postBody: this.form.value }).subscribe(response => {
        this.appUtilService.dialog({ type: "success", title: response.responseMessage });
        this.findExistingMultiplex();
        this.initializeForm(this.form.get("action").value);
      }, error => this.appUtilService.dialog({ type: "error", title: error.errorMessage }) );
    }
  }

  delete(): void {
    let id: string = this.form.get("id").value;
    if (id) {
      let name: string = this.form.get("multiplexName").value;
      this.appUtilService.confirm({
        title: `Deleting a multiplex record will delete screen records 
        and all the movies will be cancelled on the screens. Confirm delete ` + name + ` ?`,
        ok: () => { this.confirmDelete(); }
      });
    } else {
      this.appUtilService.dialog({ type: "warning", title: "Select a multiplex before deleting!" });
    }
  }

  confirmDelete(): void {
    this.apiService.post({ url: "/multiplex/manage/delete", postBody: this.form.value }).subscribe(response => {
      this.appUtilService.dialog({ type: "success", title: "Multiplex deleted successfuly!" });
      this.initializeForm("update");
      this.findExistingMultiplex();
    }, error => this.appUtilService.dialog({ type: "error", title: error.errorMessage }) );
  }

  setMultiplexDetails(): void {
    let id: string = this.form.get("id").value;
    if (id) {
      this.apiService.get({ url: "/multiplex/manage/find/" + id }).subscribe(response => {
        if (response) {
          this.form.get("multiplexName").setValue(response.multiplexName);
          this.form.get("address").setValue(response.address);
          this.form.get("description").setValue(response.description);
          this.setScreens(response.screens);
        } else {
          this.initializeForm("update");
          this.appUtilService.dialog({ type: "error", title: "No such multiplex found!" });
          this.findExistingMultiplex();
        }
      }, error => {
        this.appUtilService.dialog({ type: "error", title: error.errorMessage });
        this.findExistingMultiplex();
      });
    }
  }

  setScreens(screens: any[]): void {
    let screenFormArray: FormArray = this.form.get("screens") as FormArray;
    let length: number = screenFormArray.length;
    if (length > 0) {
      for (let i = 0; i < length; i++) {
        screenFormArray.removeAt(i);
      }
    }
    screens.forEach(s => screenFormArray.push(this.screenRowWithValues(s.id, s.name)));
  }

  findExistingMultiplex(): void {
    this.multiplexes = [];
    this.apiService.get({ url: "/multiplex/manage/all-multiplex" }).subscribe(response => {
      if (response && response.multiplexes.length > 0) {
        this.multiplexes = (response.multiplexes as Array<any>);
      }
    });
  }

}
