import React, { Component, Fragment } from 'react';
import '../css/perfil-pro.css';
import '../css/bootstrap.css';
import '../css/padroes.css';

export class CapaPerfilPro extends Component{
   render(){
    return(
       <Fragment>
            <div class="capa-perfil-pro">
            </div>
            <div class="caixa-perfil">
            <div class="avatar">
            </div>
            
                <h1 class="nome-cliente flex-center">{this.props.titulo}</h1>
                <div class="caixa-informacoes-basicas">
                    <div class="caixa-info flex-center">
                        <div class="caixa-info1">Type something here</div>
                        <div class="caixa-info1">Type something here</div>
                    </div>
                    <div class="caixa-info flex-center">
                        <div class="caixa-info1">Type something here</div>
                        <div class="caixa-info1">Type something here</div>
                    </div>
                </div>
            </div>
        </Fragment>
    );
   }

}

export default CapaPerfilPro;