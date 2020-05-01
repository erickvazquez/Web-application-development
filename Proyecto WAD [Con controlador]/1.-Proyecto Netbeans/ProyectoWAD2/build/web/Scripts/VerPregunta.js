const {
  Button,
  Icon,
  Typography,
  Paper,
  withStyles,
  Grid,
  ButtonBase,
  Checkbox
} = window['material-ui'];

const styles = theme => ({
    
  root: {
    textAlign: 'center',
    paddingTop: theme.spacing.unit * 20,
  },
  icon: {
    margin: theme.spacing.unit,
    fontSize: 32,
  },
  image: {
    position: 'relative',
    maxHeight: 200,
    maxWidth: 500
  },
  main: {
    width: 'auto',
    display: 'block', // Fix IE 11 issue.
    marginLeft: theme.spacing.unit * 3,
    marginRight: theme.spacing.unit * 3,
    [theme.breakpoints.up(400 + theme.spacing.unit * 3 * 2)]: {
      width: 400,
      marginLeft: 'auto',
      marginRight: 'auto',
    },
  },
  paper: {
    padding: theme.spacing.unit * 2,
    margin: 'auto',
    minWidth: 50,
  },
  button: {
    margin: theme.spacing.unit,
  },
});
class Index extends React.Component {
    
    constructor(props){
      super(props);
      this.state = {
          id: "",
          pregunta: "",
          respuestas:[],
          multimedia: "",
          tipo: "",
          codMulti: ""
      }
    }
    
    tipodeMultimedia(tipo){
        const { classes } = this.props;
        console.log("Se llamó a la función, tipo es: "+tipo);
        if(tipo==="jpg" || tipo==="png"){
                return (<ButtonBase className={classes.image}> <img className={classes.img} src={"multimedia/"+this.state.multimedia} /> </ButtonBase>);
            }else if(tipo==="mp4"){
                return (<video controls> <source src={"multimedia/"+this.state.multimedia} type='video/mp4' /> </video>);
            }else if(tipo==="mp3"){
                return (<audio controls> <source src={"multimedia/"+this.state.multimedia} type='audio/mpeg' /> </audio>);
            }
    }
    
    componentDidMount = () => {
      fetch("preguntamc.json", {
          method: "GET"
      }).then(response => response.json())
        .then( json =>{
            console.log(json);
            var x = json.multimedia.substring(json.multimedia.length -3, json.multimedia.length);
            console.log(x);
            this.setState ({
                id: json.id,
                pregunta: json.pregunta,
                respuestas: json.resp,
                multimedia: json.multimedia,
                tipo: x
      });return x;});
  }
  
  render() {
    const {classes} = this.props;
    return (
     <Grid item xs={12}>
      <Paper className={classes.paper}>    
        <Grid container spacing={16} alignItems="center" justify="center">
          <Grid item xs={12} sm container>
            <Grid item xs container direction="column" spacing={16}>
              <Grid item xs>
                <Typography gutterBottom variant="h6">
                  {this.state.id}
                </Typography>
                <Typography variant="h5">{this.state.pregunta}</Typography>
                {this.state.respuestas.map(res => (
                  <Grid container item xs={24} spacing={24}>
                    <Grid item xs={8}>
                      <Paper className={classes.paper}>
                        <Grid container direction="row" alignItems="center">
                            <Grid item>
                                <Checkbox color="primary" />
                            </Grid>
                            <Grid item>
                                <Typography variant="body1">{res}</Typography>
                            </Grid>
                        </Grid>
                      </Paper>
                    </Grid>
                  </Grid>
                ))}
              </Grid>
            </Grid>
          </Grid>
          <Grid item justify="center">
              {this.tipodeMultimedia(this.state.tipo)}
          </Grid>
        </Grid>
      </Paper>
      <a href="TablaPreguntas.jsp">
            <Button fullWidth variant="contained" color="primary">Regresar</Button>
      </a>
      </Grid>
    );
  }
}
const VerPregunta = withStyles(styles)(Index);
ReactDOM.render(<VerPregunta />, document.getElementById('root'));